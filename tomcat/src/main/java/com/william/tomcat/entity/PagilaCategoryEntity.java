package com.william.tomcat.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "category")
@Getter
@Setter
public class PagilaCategoryEntity {
	@Id
	@Column(name = "category_id", nullable = false)
	private Integer categoryId;

	@Column(name = "name", nullable = false)
	private String name;
}
