package com.william.tomcat.entity;

import java.io.Serializable;
import java.util.Objects;

public class PagilaFilmActorId implements Serializable {
	private Integer filmId;
	private Integer actorId;

	public PagilaFilmActorId() {
	}

	public PagilaFilmActorId(Integer filmId, Integer actorId) {
		this.filmId = filmId;
		this.actorId = actorId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		PagilaFilmActorId that = (PagilaFilmActorId) o;
		return Objects.equals(filmId, that.filmId) && Objects.equals(actorId, that.actorId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(filmId, actorId);
	}
}
