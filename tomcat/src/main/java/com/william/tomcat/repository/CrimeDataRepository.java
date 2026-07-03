package com.william.tomcat.repository;

import com.william.tomcat.entity.CrimeDataEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CrimeDataRepository extends JpaRepository<CrimeDataEntity, Long> {

    List<CrimeDataEntity> findByArea(Long area, Pageable pageable);
    
    List<CrimeDataEntity> findByCrmCd(Long crmCd, Pageable pageable);
}
