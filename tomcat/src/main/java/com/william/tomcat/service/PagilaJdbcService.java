package com.william.tomcat.service;

import com.william.tomcat.dto.ActorDto;
import com.william.tomcat.dto.CustomerPaymentDto;
import com.william.tomcat.dto.FilmRentalDto;
import com.william.tomcat.dto.FilmWithCategoryDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PagilaJdbcService implements PagilaService {

	private final JdbcTemplate jdbcTemplate;

	public PagilaJdbcService(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<FilmWithCategoryDto> films(int limit, int offset) {
		String sql = """
				SELECT f.film_id,
				       f.title,
				       f.release_year,
				       f.rental_rate,
				       f.length,
				       f.rating,
				       c.name AS category
				FROM film f
				JOIN film_category fc ON fc.film_id = f.film_id
				JOIN category c ON c.category_id = fc.category_id
				ORDER BY f.film_id
				LIMIT ? OFFSET ?
				""";
		RowMapper<FilmWithCategoryDto> rowMapper = (rs, rowNum) -> new FilmWithCategoryDto(
			rs.getInt("film_id"),
			rs.getString("title"),
			rs.getInt("release_year"),
			rs.getBigDecimal("rental_rate"),
			rs.getInt("length"),
			rs.getString("rating"),
			rs.getString("category")
		);
		return jdbcTemplate.query(sql, rowMapper, limit, offset);
	}

	@Override
	public List<FilmRentalDto> topRentals(int limit) {
		String sql = """
				SELECT f.film_id,
				       f.title,
				       COUNT(r.rental_id) AS rental_count
				FROM film f
				JOIN inventory i ON i.film_id = f.film_id
				JOIN rental r ON r.inventory_id = i.inventory_id
				GROUP BY f.film_id, f.title
				ORDER BY rental_count DESC
				LIMIT ?
				""";
		RowMapper<FilmRentalDto> rowMapper = (rs, rowNum) -> new FilmRentalDto(
			rs.getInt("film_id"),
			rs.getString("title"),
			rs.getLong("rental_count")
		);
		return jdbcTemplate.query(sql, rowMapper, limit);
	}

	@Override
	public List<CustomerPaymentDto> topCustomers(int limit) {
		String sql = """
				SELECT c.customer_id,
				       c.first_name,
				       c.last_name,
				       SUM(p.amount) AS total_amount,
				       COUNT(p.payment_id) AS payment_count
				FROM customer c
				LEFT JOIN payment p ON p.customer_id = c.customer_id
				GROUP BY c.customer_id, c.first_name, c.last_name
				ORDER BY SUM(p.amount) DESC
				LIMIT ?
				""";
		RowMapper<CustomerPaymentDto> rowMapper = (rs, rowNum) -> new CustomerPaymentDto(
			rs.getInt("customer_id"),
			rs.getString("first_name"),
			rs.getString("last_name"),
			rs.getBigDecimal("total_amount"),
			rs.getLong("payment_count")
		);
		return jdbcTemplate.query(sql, rowMapper, limit);
	}

	@Override
	public List<ActorDto> actorsByFilm(int filmId) {
		String sql = """
				SELECT a.actor_id,
				       a.first_name,
				       a.last_name
				FROM actor a
				JOIN film_actor fa ON fa.actor_id = a.actor_id
				WHERE fa.film_id = ?
				ORDER BY a.actor_id
				""";
		RowMapper<ActorDto> rowMapper = (rs, rowNum) -> new ActorDto(
			rs.getInt("actor_id"),
			rs.getString("first_name"),
			rs.getString("last_name")
		);
		return jdbcTemplate.query(sql, rowMapper, filmId);
	}

	@Override
	public QueryMode getQueryMode() {
		return QueryMode.JDBC;
	}
}
