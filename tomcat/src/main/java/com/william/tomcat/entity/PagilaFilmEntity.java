package com.william.tomcat.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "film")
@Getter
@Setter
public class PagilaFilmEntity {
	@Id
	@Column(name = "film_id", nullable = false)
	private Integer filmId;

	@Column(name = "title", nullable = false)
	private String title;

	@Column(name = "release_year")
	private Integer releaseYear;

	@Column(name = "rental_rate")
	private BigDecimal rentalRate;

	@Column(name = "length")
	private Integer length;

	@Column(name = "rating")
	private String rating;
}
