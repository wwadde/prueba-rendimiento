package com.william.webflux.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("actor")
public class PagilaActorEntity {
	@Id
	@Column("actor_id")
	private Integer actorId;

	@Column("first_name")
	private String firstName;

	@Column("last_name")
	private String lastName;
}
