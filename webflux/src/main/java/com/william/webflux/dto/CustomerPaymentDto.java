package com.william.webflux.dto;

import java.math.BigDecimal;

public record CustomerPaymentDto(
	Integer customerId,
	String firstName,
	String lastName,
	BigDecimal totalAmount,
	Long paymentCount
) {}
