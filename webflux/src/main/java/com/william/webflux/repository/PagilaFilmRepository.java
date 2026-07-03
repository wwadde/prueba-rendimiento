package com.william.webflux.repository;

import com.william.webflux.dto.FilmRentalDto;
import com.william.webflux.dto.FilmWithCategoryDto;
import com.william.webflux.entity.PagilaFilmEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface PagilaFilmRepository extends R2dbcRepository<PagilaFilmEntity, Integer> {
	@Query("""
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
			LIMIT :limit OFFSET :offset
			""")
	Flux<FilmWithCategoryDto> findFilmsWithCategories(int limit, int offset);

	@Query("""
			SELECT f.film_id,
			       f.title,
			       COUNT(r.rental_id) AS rental_count
			FROM film f
			JOIN inventory i ON i.film_id = f.film_id
			JOIN rental r ON r.inventory_id = i.inventory_id
			GROUP BY f.film_id, f.title
			ORDER BY rental_count DESC
			LIMIT :limit
			""")
	Flux<FilmRentalDto> findTopRentals(int limit);
}
