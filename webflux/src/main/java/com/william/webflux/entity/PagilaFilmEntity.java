package com.william.webflux.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("film")
public class PagilaFilmEntity {
	@Id
	@Column("film_id")
	private Integer filmId;

	@Column("title")
	private String title;

	@Column("release_year")
	private Integer releaseYear;

	@Column("rental_rate")
	private BigDecimal rentalRate;

	@Column("length")
	private Integer length;

	@Column("rating")
	private String rating;
}
