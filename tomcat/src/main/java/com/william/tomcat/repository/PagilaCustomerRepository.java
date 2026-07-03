package com.william.tomcat.repository;

import com.william.tomcat.dto.CustomerPaymentDto;
import com.william.tomcat.entity.PagilaCustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PagilaCustomerRepository extends JpaRepository<PagilaCustomerEntity, Integer> {
	@Query(value = """
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
			""", nativeQuery = true)
	List<CustomerPaymentDto> findTopCustomers(int limit);
}
