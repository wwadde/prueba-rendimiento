package com.william.webflux.repository;

import com.william.webflux.dto.CustomerPaymentDto;
import com.william.webflux.entity.PagilaCustomerEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface PagilaCustomerRepository extends R2dbcRepository<PagilaCustomerEntity, Integer> {
	@Query("""
			SELECT c.customer_id,
			       c.first_name,
			       c.last_name,
			       COALESCE(SUM(p.amount), 0) AS total_amount,
			       COUNT(p.payment_id) AS payment_count
			FROM customer c
			LEFT JOIN payment p ON p.customer_id = c.customer_id
			GROUP BY c.customer_id, c.first_name, c.last_name
			ORDER BY total_amount DESC
			LIMIT :limit
			""")
	Flux<CustomerPaymentDto> findTopCustomers(int limit);
}
