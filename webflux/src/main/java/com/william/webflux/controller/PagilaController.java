package com.william.webflux.controller;

import com.william.webflux.dto.ActorDto;
import com.william.webflux.dto.CustomerPaymentDto;
import com.william.webflux.dto.FilmRentalDto;
import com.william.webflux.dto.FilmWithCategoryDto;
import com.william.webflux.service.PagilaQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/v1/pagila")
@Validated
@Tag(name = "Pagila", description = "Endpoints para la base de datos de películas (Pagila) de manera asincrona")
public class PagilaController {
        private final PagilaQueryService pagilaQueryService;

        public PagilaController(PagilaQueryService pagilaQueryService) {
                this.pagilaQueryService = pagilaQueryService;
        }

        @GetMapping("/films")
        @Operation(summary = "Listar películas", description = "Obtiene un Flux de películas incluyendo su categoría.")
        public Flux<FilmWithCategoryDto> films(
                        @RequestParam(defaultValue = "100") @Min(1) @Max(1000) int limit,
                        @RequestParam(defaultValue = "0") @Min(0) int offset) {
                return pagilaQueryService.films(limit, offset);
        }

        @GetMapping("/films/top-rentals")
        @Operation(summary = "Top de películas mas rentadas", description = "Obtiene un Flux de las películas con mayor cantidad de rentas.")
        public Flux<FilmRentalDto> topRentals(
                        @RequestParam(defaultValue = "50") @Min(1) @Max(500) int limit) {
                return pagilaQueryService.topRentals(limit);
        }

        @GetMapping("/customers/top-payments")
        @Operation(summary = "Top de clientes con más pagos", description = "Obtiene un Flux de los clientes que más pagos han hecho.")
        public Flux<CustomerPaymentDto> topCustomers(
                        @RequestParam(defaultValue = "50") @Min(1) @Max(500) int limit) {
                return pagilaQueryService.topCustomers(limit);
        }

        @GetMapping("/films/{filmId}/actors")
        @Operation(summary = "Actores por película", description = "Obtiene un Flux de actores que participaron en una película específica.")
        public Flux<ActorDto> actorsByFilm(
                        @PathVariable @Min(1) int filmId) {
                return pagilaQueryService.actorsByFilm(filmId);
        }
}

