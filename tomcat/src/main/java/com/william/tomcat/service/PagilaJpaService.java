package com.william.tomcat.service;

import com.william.tomcat.dto.ActorDto;
import com.william.tomcat.dto.CustomerPaymentDto;
import com.william.tomcat.dto.FilmRentalDto;
import com.william.tomcat.dto.FilmWithCategoryDto;
import com.william.tomcat.repository.PagilaActorRepository;
import com.william.tomcat.repository.PagilaCategoryRepository;
import com.william.tomcat.repository.PagilaCustomerRepository;
import com.william.tomcat.repository.PagilaFilmRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PagilaJpaService implements PagilaService {

	private final PagilaFilmRepository filmRepository;
	private final PagilaActorRepository actorRepository;
	private final PagilaCustomerRepository customerRepository;

	public PagilaJpaService(PagilaFilmRepository filmRepository,
							PagilaActorRepository actorRepository,
							PagilaCustomerRepository customerRepository) {
		this.filmRepository = filmRepository;
		this.actorRepository = actorRepository;
		this.customerRepository = customerRepository;
	}

	@Override
	public List<FilmWithCategoryDto> films(int limit, int offset) {
		return filmRepository.findFilmsWithCategories(limit, offset);
	}

	@Override
	public List<FilmRentalDto> topRentals(int limit) {
		return filmRepository.findTopRentals(limit);
	}

	@Override
	public List<CustomerPaymentDto> topCustomers(int limit) {
		return customerRepository.findTopCustomers(limit);
	}

	@Override
	public List<ActorDto> actorsByFilm(int filmId) {
		return actorRepository.findActorsByFilmId(filmId);
	}

	@Override
	public QueryMode getQueryMode() {
		return QueryMode.JPA;
	}
}
