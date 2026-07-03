package com.william.tomcat.dto;

import java.math.BigDecimal;

public record FilmWithCategoryDto(
	Integer filmId,
	String title,
	Integer releaseYear,
	BigDecimal rentalRate,
	Integer length,
	String rating,
	String category
) {}
