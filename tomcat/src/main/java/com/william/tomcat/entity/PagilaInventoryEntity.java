package com.william.tomcat.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "inventory")
@Getter
@Setter
public class PagilaInventoryEntity {
	@Id
	@Column(name = "inventory_id", nullable = false)
	private Integer inventoryId;

	@Column(name = "film_id", nullable = false)
	private Integer filmId;

	@Column(name = "store_id", nullable = false)
	private Integer storeId;
}
