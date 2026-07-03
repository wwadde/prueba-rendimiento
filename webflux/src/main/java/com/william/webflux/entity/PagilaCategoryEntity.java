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
@Table("category")
public class PagilaCategoryEntity {
	@Id
	@Column("category_id")
	private Integer categoryId;

	@Column("name")
	private String name;
}
