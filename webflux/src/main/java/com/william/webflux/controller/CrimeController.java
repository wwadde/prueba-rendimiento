package com.william.webflux.controller;

import com.william.webflux.service.CrimeQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/crimes")
@Validated
@Tag(name = "Crime Data", description = "Endpoints para consultar datos de crimenes de manera asincrona")
public class CrimeController {

    private final CrimeQueryService crimeQueryService;

    public CrimeController(CrimeQueryService crimeQueryService) {
        this.crimeQueryService = crimeQueryService;
    }

    @GetMapping
    @Operation(summary = "Obtener lista de crimenes reactiva", description = "Retorna un flujo de datos (Flux) de los crimenes en forma paginada.")
    public Flux<Map<String, Object>> getCrimes(
            @RequestParam(defaultValue = "100") @Min(1) @Max(1000) int limit,
            @RequestParam(defaultValue = "0") @Min(0) int offset) {
        return crimeQueryService.getCrimes(limit, offset);
    }

    @GetMapping("/count")
    @Operation(summary = "Obtener conteo de crimenes", description = "Retorna un Mono con la cantidad total de crimenes en disco.")
    public Mono<Map<String, Long>> getCrimeCount() {
        return crimeQueryService.getCrimeCount()
                .map(count -> Map.of("count", count));
    }
}
