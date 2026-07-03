package com.william.tomcat.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "film_category")
@IdClass(PagilaFilmCategoryId.class)
@Getter
@Setter
public class PagilaFilmCategoryEntity {
	@Id
	@Column(name = "film_id", nullable = false)
	private Integer filmId;

	@Id
	@Column(name = "category_id", nullable = false)
	private Integer categoryId;
}
