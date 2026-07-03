package com.william.tomcat.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "film_actor")
@IdClass(PagilaFilmActorId.class)
@Getter
@Setter
public class PagilaFilmActorEntity {
	@Id
	@Column(name = "film_id", nullable = false)
	private Integer filmId;

	@Id
	@Column(name = "actor_id", nullable = false)
	private Integer actorId;
}
