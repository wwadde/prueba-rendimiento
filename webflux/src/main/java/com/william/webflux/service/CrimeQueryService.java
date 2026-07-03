package com.william.webflux.service;

import com.william.webflux.repository.CrimeDataRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
public class CrimeQueryService {

	private final CrimeDataRepository crimeDataRepository;

	public CrimeQueryService(CrimeDataRepository crimeDataRepository) {
		this.crimeDataRepository = crimeDataRepository;
	}

	public Flux<Map<String, Object>> getCrimes(int limit, int offset) {
		return crimeDataRepository.findCrimes(limit, offset);
	}

	public Mono<Long> getCrimeCount() {
		return crimeDataRepository.countCrimes();
	}
}
