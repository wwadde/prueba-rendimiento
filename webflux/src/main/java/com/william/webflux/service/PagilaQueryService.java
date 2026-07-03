package com.william.webflux.service;

import com.william.webflux.dto.ActorDto;
import com.william.webflux.dto.CustomerPaymentDto;
import com.william.webflux.dto.FilmRentalDto;
import com.william.webflux.dto.FilmWithCategoryDto;
import com.william.webflux.repository.PagilaActorRepository;
import com.william.webflux.repository.PagilaCustomerRepository;
import com.william.webflux.repository.PagilaFilmRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class PagilaQueryService {
	private final PagilaFilmRepository filmRepository;
	private final PagilaActorRepository actorRepository;
	private final PagilaCustomerRepository customerRepository;

	public PagilaQueryService(PagilaFilmRepository filmRepository,
							   PagilaActorRepository actorRepository,
							   PagilaCustomerRepository customerRepository) {
		this.filmRepository = filmRepository;
		this.actorRepository = actorRepository;
		this.customerRepository = customerRepository;
	}

	public Flux<FilmWithCategoryDto> films(int limit, int offset) {
		return filmRepository.findFilmsWithCategories(limit, offset);
	}

	public Flux<FilmRentalDto> topRentals(int limit) {
		return filmRepository.findTopRentals(limit);
	}

	public Flux<CustomerPaymentDto> topCustomers(int limit) {
		return customerRepository.findTopCustomers(limit);
	}

	public Flux<ActorDto> actorsByFilm(int filmId) {
		return actorRepository.findActorsByFilmId(filmId);
	}
}
