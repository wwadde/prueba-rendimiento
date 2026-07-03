package com.william.tomcat.service;

import com.william.tomcat.dto.ActorDto;
import com.william.tomcat.dto.CustomerPaymentDto;
import com.william.tomcat.dto.FilmRentalDto;
import com.william.tomcat.dto.FilmWithCategoryDto;

import java.util.List;

public interface PagilaService {
	List<FilmWithCategoryDto> films(int limit, int offset);

	List<FilmRentalDto> topRentals(int limit);

	List<CustomerPaymentDto> topCustomers(int limit);

	List<ActorDto> actorsByFilm(int filmId);

	QueryMode getQueryMode();
}
