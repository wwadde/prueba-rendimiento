package com.william.tomcat.repository;

import com.william.tomcat.entity.PagilaCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagilaCategoryRepository extends JpaRepository<PagilaCategoryEntity, Integer> {
}
