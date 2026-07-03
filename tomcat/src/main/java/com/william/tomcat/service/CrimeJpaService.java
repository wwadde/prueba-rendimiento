package com.william.tomcat.service;

import com.william.tomcat.entity.CrimeDataEntity;
import com.william.tomcat.repository.CrimeDataRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class CrimeJpaService implements CrimeService {

    private final CrimeDataRepository crimeDataRepository;

    public CrimeJpaService(CrimeDataRepository crimeDataRepository) {
        this.crimeDataRepository = crimeDataRepository;
    }

    @Override
    public Page<CrimeDataEntity> getCrimes(int page, int size) {
        log.info("Buscando con JPA");
        return crimeDataRepository.findAll(PageRequest.of(page, size));
    }

    @Override
    public long getCrimeCount() {
        return crimeDataRepository.count();
    }

    @Override
    public QueryMode getQueryMode() {
        return QueryMode.JPA;
    }

}
