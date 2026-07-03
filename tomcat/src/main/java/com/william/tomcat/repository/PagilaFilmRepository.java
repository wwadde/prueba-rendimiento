package com.william.tomcat.repository;

import com.william.tomcat.dto.FilmRentalDto;
import com.william.tomcat.dto.FilmWithCategoryDto;
import com.william.tomcat.entity.PagilaFilmEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PagilaFilmRepository extends JpaRepository<PagilaFilmEntity, Integer> {
	@Query(value = """
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
			""", nativeQuery = true)
	List<FilmWithCategoryDto> findFilmsWithCategories(int limit, int offset);

	@Query(value = """
			SELECT f.film_id,
			       f.title,
			       COUNT(r.rental_id) AS rental_count
			FROM film f
			JOIN inventory i ON i.film_id = f.film_id
			JOIN rental r ON r.inventory_id = i.inventory_id
			GROUP BY f.film_id, f.title
			ORDER BY rental_count DESC
			LIMIT :limit
			""", nativeQuery = true)
	List<FilmRentalDto> findTopRentals(int limit);
}
