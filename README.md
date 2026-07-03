# Tomcat vs WebFlux - CSV performance setup

## Objetivo
Comparar rendimiento entre Tomcat (Spring MVC) y WebFlux con endpoints REST equivalentes, usando los mismos datos en PostgreSQL y metricas con Prometheus/Grafana.

## Requisitos
- Java 25
- Docker Desktop
- Python 3.10+

## Configuracion rapida
1) Levanta PostgreSQL + Prometheus + Grafana:
```
docker compose up -d
```


4) Ejecuta las apps:
```
cd tomcat
.\gradlew bootRun
```

En otra terminal:
```
cd webflux
.\gradlew bootRun
```

## Estructura de paquetes (interna)
- `tomcat/src/main/java/com/william/tomcat/`
  - `controller/`, `service/`, `repository/`, `entity/`, `dto/`, `exception/`
- `webflux/src/main/java/com/william/webflux/`
  - `controller/`, `service/`, `dto/`, `exception/`

## Endpoints
Todos los endpoints son iguales en ambas apps.
- `GET /api/v1/datasets`
- `GET /api/v1/datasets/{dataset}/count`
- `GET /api/v1/datasets/{dataset}/records?limit=100&offset=0`
- `GET /api/v1/datasets/{dataset}/columns`

En Tomcat puedes elegir el modo de acceso con `mode=jdbc|jpa` (por defecto `jdbc`):
- `GET /api/v1/datasets?mode=jpa`
- `GET /api/v1/datasets/{dataset}/records?limit=100&offset=0&mode=jpa`

Datasets disponibles (se listan desde la tabla `dataset_metadata` despues de cargar los CSV):
- `crime_data_from_2020_to_2024`

Endpoints Pagila:
- `GET /api/v1/pagila/films?limit=100&offset=0`
- `GET /api/v1/pagila/films/top-rentals?limit=50`
- `GET /api/v1/pagila/customers/top-payments?limit=50`
- `GET /api/v1/pagila/films/{filmId}/actors`

## Metricas
- Tomcat: `http://localhost:8080/actuator/prometheus`
- WebFlux: `http://localhost:8081/actuator/prometheus`
- Prometheus: `http://localhost:9090`
- Grafana: `http://localhost:3000` (admin/admin)




Pagila (carga manual opcional):
- El script `scripts/load_csvs.py` ya carga `pagila-schema.sql` y `pagila-data.sql` usando `docker exec`.
- Si tu contenedor no se llama `perf_postgres`, usa `--container <nombre>`.
- Para omitir pagila en una corrida, usa `--skip-pagila`.

Fuente: https://github.com/devrimgunduz/pagila
Descarga dataset csv: https://data.lacity.org/api/views/2nrs-mtv8/rows.csv?accessType=DOWNLOAD
```
python -m venv .venv
```
```
.\.venv\Scripts\Activate
```
```
python -m pip install -r requirements.txt
```
```
python load_data.py   
```


http://localhost:8080/swagger-ui/index.html