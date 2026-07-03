package com.william.tomcat.repository;

import com.william.tomcat.entity.PagilaFilmCategoryEntity;
import com.william.tomcat.entity.PagilaFilmCategoryId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PagilaFilmCategoryRepository extends JpaRepository<PagilaFilmCategoryEntity, PagilaFilmCategoryId> {
	@Query("SELECT fc.filmId FROM PagilaFilmCategoryEntity fc WHERE fc.categoryId IN " +
		   "(SELECT fc2.categoryId FROM PagilaFilmCategoryEntity fc2 WHERE fc2.filmId = :filmId)")
	List<Integer> findFilmIdsByCategoryOfFilm(Integer filmId);
}
