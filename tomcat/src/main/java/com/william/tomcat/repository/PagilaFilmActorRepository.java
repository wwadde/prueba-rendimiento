package com.william.tomcat.repository;

import com.william.tomcat.entity.PagilaFilmActorEntity;
import com.william.tomcat.entity.PagilaFilmActorId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PagilaFilmActorRepository extends JpaRepository<PagilaFilmActorEntity, PagilaFilmActorId> {
	@Query("SELECT fa.actorId FROM PagilaFilmActorEntity fa WHERE fa.filmId = :filmId ORDER BY fa.actorId")
	List<Integer> findActorIdsByFilmId(Integer filmId);
}
