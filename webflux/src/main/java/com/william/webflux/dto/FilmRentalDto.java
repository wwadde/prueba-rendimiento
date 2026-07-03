package com.william.webflux.dto;

public record FilmRentalDto(
	Integer filmId,
	String title,
	Long rentalCount
) {}
