package com.william.tomcat.repository;

import com.william.tomcat.entity.PagilaInventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagilaInventoryRepository extends JpaRepository<PagilaInventoryEntity, Integer> {
}
