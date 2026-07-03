package com.william.tomcat.repository;

import com.william.tomcat.entity.PagilaPaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagilaPaymentRepository extends JpaRepository<PagilaPaymentEntity, Integer> {
}
