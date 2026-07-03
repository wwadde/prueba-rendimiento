package com.william.webflux.repository;

import com.william.webflux.entity.CrimeDataEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@Repository
public interface CrimeDataRepository extends R2dbcRepository<CrimeDataEntity, Long> {
	@Query("""
			SELECT *
			FROM crime_data_from_2020_to_2024
			ORDER BY dr_no
			LIMIT :limit OFFSET :offset
			""")
	Flux<Map<String, Object>> findCrimes(int limit, int offset);

	@Query("SELECT COUNT(*) as count FROM crime_data_from_2020_to_2024")
	Mono<Long> countCrimes();
}
