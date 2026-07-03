import csv
import sys
from pathlib import Path

import psycopg
from psycopg import sql

import requests

CSV_URL = "https://data.lacity.org/api/views/2nrs-mtv8/rows.csv?accessType=DOWNLOAD"

def download_dataset(csv_path: Path):
    if csv_path.exists():
        return

    print("Downloading crime dataset...")

    with requests.get(CSV_URL, stream=True, timeout=60) as response:
        response.raise_for_status()

        with csv_path.open("wb") as file:
            for chunk in response.iter_content(chunk_size=1024 * 1024):
                if chunk:
                    file.write(chunk)

    print("Download complete.")

CSV_FILE = "Crime_Data_from_2020_to_2024.csv"
TABLE_NAME = "crime_data_from_2020_to_2024"
PAGILA_SCHEMA_SQL = "pagila-schema.sql"
PAGILA_DATA_SQL = "pagila-data.sql"
CONTAINER_NAME = "perf_postgres"
DB_CONNINFO = "host=localhost port=15432 dbname=pruebarendimiento user=postgres password=admin"
ADMIN_DB_CONNINFO = "host=localhost port=15432 dbname=postgres user=postgres password=admin"

CRIME_DATA_INT_COLUMNS = {
	"DR_NO",
	"TIME OCC",
	"AREA",
	"Rpt Dist No",
	"Part 1-2",
	"Crm Cd",
	"Vict Age",
	"Premis Cd",
	"Weapon Used Cd",
	"Crm Cd 1",
	"Crm Cd 2",
}


def normalize_column(name, existing):
	base = name.strip().lower().replace(" ", "_").replace("-", "_")
	base = "_".join(part for part in base.split("_") if part)
	if not base:
		base = "col"
	if base[0].isdigit():
		base = f"col_{base}"
	candidate = base
	counter = 2
	while candidate in existing:
		candidate = f"{base}_{counter}"
		counter += 1
	existing.add(candidate)
	return candidate


def run_psql_file(sql_path, container_name, dbname, user):
	import subprocess

	command = [
		"docker",
		"exec",
		"-i",
		container_name,
		"psql",
		"-U",
		user,
		"-d",
		dbname,
	]
	with sql_path.open("r", encoding="utf-8", errors="replace") as handle:
		result = subprocess.run(command, stdin=handle, capture_output=True, text=True)
	if result.returncode != 0:
		raise RuntimeError(
			"psql failed for "
			f"{sql_path.name}: {result.stderr.strip() or result.stdout.strip()}"
		)


def create_crime_table(cursor, table_name, header_names, reset):
	existing = set()
	columns = [normalize_column(name, existing) for name in header_names]
	column_defs = [
		(columns[index], "BIGINT" if header_names[index] in CRIME_DATA_INT_COLUMNS else "TEXT")
		for index in range(len(columns))
	]
	if reset:
		cursor.execute(sql.SQL("DROP TABLE IF EXISTS {} CASCADE").format(sql.Identifier(table_name)))
	columns_sql = sql.SQL(", ").join(
		[sql.SQL("{} ").format(sql.Identifier(col)) + sql.SQL(col_type)
		 for col, col_type in column_defs]
	)
	cursor.execute(
		sql.SQL("CREATE TABLE IF NOT EXISTS {} ({})")
		.format(sql.Identifier(table_name), columns_sql)
	)
	return columns


def load_csv(cursor, csv_path, table_name, reset):
	print(f"Loading {table_name} from {csv_path}...")
	with csv_path.open("r", newline="", encoding="utf-8", errors="replace") as handle:
		header_line = handle.readline()
		if not header_line:
			print(f"Skipping {table_name}: empty file")
			return
		header_names = next(csv.reader([header_line]))
		columns = create_crime_table(cursor, table_name, header_names, reset)
		copy_sql = sql.SQL("COPY {} FROM STDIN WITH (FORMAT csv, HEADER true)").format(
			sql.Identifier(table_name)
		)
		handle.seek(0)
		with cursor.copy(copy_sql) as copy:
			while True:
				chunk = handle.read(8192)
				if not chunk:
					break
				copy.write(chunk)
	print(f"Loaded {table_name}.")


def ask_yes_no(prompt, default="n"):
	answer = input(f"{prompt} [{'Y/n' if default == 'y' else 'y/N'}]: ").strip().lower()
	if not answer:
		answer = default
	return answer in {"y", "yes"}


def table_exists(cursor, table_name):
	cursor.execute("SELECT to_regclass(%s)", (f"public.{table_name}",))
	return cursor.fetchone()[0] is not None


def ensure_database():
	with psycopg.connect(ADMIN_DB_CONNINFO) as conn:
		conn.autocommit = True
		with conn.cursor() as cursor:
			cursor.execute("SELECT 1 FROM pg_database WHERE datname = 'pruebarendimiento'")
			if cursor.fetchone():
				return
			cursor.execute("CREATE DATABASE pruebarendimiento")


def main():
	root = Path(__file__).resolve().parent

	ensure_database()
	schema_path = root / PAGILA_SCHEMA_SQL
	data_path = root / PAGILA_DATA_SQL
	if schema_path.exists() and data_path.exists():
		print("Loading pagila schema and data...")
		with psycopg.connect(DB_CONNINFO) as conn:
			with conn.cursor() as cursor:
				pagila_exists = table_exists(cursor, "film")
		if pagila_exists:
			if ask_yes_no("Pagila already exists. Reload schema and data?", default="n"):
				run_psql_file(schema_path, CONTAINER_NAME, "pruebarendimiento", "postgres")
				run_psql_file(data_path, CONTAINER_NAME, "pruebarendimiento", "postgres")
				print("Pagila reload complete.")
			else:
				print("Skipping pagila reload.")
		else:
			run_psql_file(schema_path, CONTAINER_NAME, "pruebarendimiento", "postgres")
			run_psql_file(data_path, CONTAINER_NAME, "pruebarendimiento", "postgres")
			print("Pagila load complete.")
	else:
		print("Pagila SQL files not found, skipping pagila load.")

	csv_path = root / CSV_FILE
	download_dataset(csv_path)
	with psycopg.connect(DB_CONNINFO) as conn:
		with conn.cursor() as cursor:
			crime_exists = table_exists(cursor, TABLE_NAME)
			if crime_exists:
				if ask_yes_no("Crime table already exists. Reload CSV?", default="n"):
					load_csv(cursor, csv_path, TABLE_NAME, reset=True)
				else:
					print("Skipping crime CSV load.")
			else:
				load_csv(cursor, csv_path, TABLE_NAME, reset=False)
			conn.commit()

	print("Done.")

if __name__ == "__main__":
	try:
		main()
	except Exception as exc:
		print(f"Error: {exc}", file=sys.stderr)
		sys.exit(1)
