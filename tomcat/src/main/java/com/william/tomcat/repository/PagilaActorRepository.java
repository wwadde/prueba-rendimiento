package com.william.tomcat.repository;

import com.william.tomcat.dto.ActorDto;
import com.william.tomcat.entity.PagilaActorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PagilaActorRepository extends JpaRepository<PagilaActorEntity, Integer> {
	@Query(value = """
			SELECT a.actor_id,
			       a.first_name,
			       a.last_name
			FROM actor a
			JOIN film_actor fa ON fa.actor_id = a.actor_id
			WHERE fa.film_id = :filmId
			ORDER BY a.actor_id
			""", nativeQuery = true)
	List<ActorDto> findActorsByFilmId(int filmId);
}
