package com.william.tomcat.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Entity
@Table(name = "rental")
@Getter
@Setter
public class PagilaRentalEntity {
	@Id
	@Column(name = "rental_id", nullable = false)
	private Integer rentalId;

	@Column(name = "rental_date", nullable = false)
	private OffsetDateTime rentalDate;

	@Column(name = "inventory_id", nullable = false)
	private Integer inventoryId;

	@Column(name = "customer_id", nullable = false)
	private Integer customerId;

	@Column(name = "return_date")
	private OffsetDateTime returnDate;
}
