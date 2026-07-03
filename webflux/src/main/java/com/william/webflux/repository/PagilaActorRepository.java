package com.william.webflux.repository;

import com.william.webflux.dto.ActorDto;
import com.william.webflux.entity.PagilaActorEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface PagilaActorRepository extends R2dbcRepository<PagilaActorEntity, Integer> {
	@Query("""
			SELECT a.actor_id,
			       a.first_name,
			       a.last_name
			FROM actor a
			JOIN film_actor fa ON fa.actor_id = a.actor_id
			WHERE fa.film_id = :filmId
			ORDER BY a.actor_id
			""")
	Flux<ActorDto> findActorsByFilmId(int filmId);
}
