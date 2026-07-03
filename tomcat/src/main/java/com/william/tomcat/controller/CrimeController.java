package com.william.tomcat.controller;

import com.william.tomcat.entity.CrimeDataEntity;
import com.william.tomcat.service.CrimeService;
import com.william.tomcat.service.QueryMode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/crimes")
@Tag(name = "Crime Data", description = "Consultar datos de crimenes desde la tabla CSV")
public class CrimeController {
    private final Map<QueryMode, CrimeService> crimeServices;

    public CrimeController(List<CrimeService> crimeServices) {
        this.crimeServices = crimeServices.stream()
                .collect(Collectors.toMap(CrimeService::getQueryMode, service -> service));
    }

    @GetMapping
    @Operation(summary = "Obtener lista de crímenes", description = "Retorna una lista paginada de crímenes. Permite elegir la implementación a utilizar (jdbc o jpa).")
    public Page<CrimeDataEntity> getCrimes(
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "100") @Min(1) @Max(1000) int size,
            @RequestParam(defaultValue = "jdbc")
            @Pattern(regexp = "(?i)^(jdbc|jpa)$", message = "Debe ser jpa o jdbc")
            String mode) {

        return crimeServices.get(QueryMode.fromValue(mode)).getCrimes(page, size);
    }

    @GetMapping("/count")
    @Operation(summary = "Obtener conteo de crímenes", description = "Retorna la cantidad total de crímenes en la base de datos.")
    public Map<String, Long> getCrimeCount(
            @RequestParam(defaultValue = "jdbc") @Pattern(regexp = "(?i)^(jdbc|jpa)$", message = "Debe ser jpa o jdbc") String mode) {
        return Map.of("count", crimeServices.get(QueryMode.fromValue(mode)).getCrimeCount());
    }
}
