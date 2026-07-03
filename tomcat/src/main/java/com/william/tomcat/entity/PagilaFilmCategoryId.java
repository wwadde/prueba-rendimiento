package com.william.tomcat.entity;

import java.io.Serializable;
import java.util.Objects;

public class PagilaFilmCategoryId implements Serializable {
	private Integer filmId;
	private Integer categoryId;

	public PagilaFilmCategoryId() {
	}

	public PagilaFilmCategoryId(Integer filmId, Integer categoryId) {
		this.filmId = filmId;
		this.categoryId = categoryId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		PagilaFilmCategoryId that = (PagilaFilmCategoryId) o;
		return Objects.equals(filmId, that.filmId) && Objects.equals(categoryId, that.categoryId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(filmId, categoryId);
	}
}
