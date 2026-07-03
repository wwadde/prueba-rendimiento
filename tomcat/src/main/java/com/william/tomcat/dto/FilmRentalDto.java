package com.william.tomcat.dto;

public record FilmRentalDto(
	Integer filmId,
	String title,
	Long rentalCount
) {}
