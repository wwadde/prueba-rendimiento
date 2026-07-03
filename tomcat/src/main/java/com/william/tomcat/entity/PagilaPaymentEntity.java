package com.william.tomcat.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "payment")
@Getter
@Setter
public class PagilaPaymentEntity {
	@Id
	@Column(name = "payment_id", nullable = false)
	private Integer paymentId;

	@Column(name = "customer_id", nullable = false)
	private Integer customerId;

	@Column(name = "rental_id", nullable = false)
	private Integer rentalId;

	@Column(name = "amount", nullable = false)
	private BigDecimal amount;

	@Column(name = "payment_date", nullable = false)
	private OffsetDateTime paymentDate;
}
