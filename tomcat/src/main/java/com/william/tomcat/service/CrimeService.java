package com.william.tomcat.service;

import com.william.tomcat.entity.CrimeDataEntity;
import org.springframework.data.domain.Page;


public interface CrimeService {
    Page<CrimeDataEntity> getCrimes(int page, int size);
    long getCrimeCount();
    QueryMode getQueryMode();
}
