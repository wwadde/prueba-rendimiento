package com.william.tomcat.controller;


import com.william.tomcat.dto.ActorDto;
import com.william.tomcat.dto.CustomerPaymentDto;
import com.william.tomcat.dto.FilmRentalDto;
import com.william.tomcat.dto.FilmWithCategoryDto;
import com.william.tomcat.service.PagilaService;
import com.william.tomcat.service.QueryMode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pagila")
@Validated
@Tag(name = "Pagila", description = "Endpoints para la base de datos de películas (Pagila)")
public class PagilaController {

        private final Map<QueryMode,PagilaService> pagilaService;

        public PagilaController(List<PagilaService> pagilaService) {
                this.pagilaService = pagilaService.stream()
                                .collect(Collectors.toMap(PagilaService::getQueryMode, service -> service));
        }

        @GetMapping("/films")
        @Operation(summary = "Listar películas", description = "Obtiene una lista paginada de películas incluyendo su categoría.")
        public List<FilmWithCategoryDto> films(
                @RequestParam(defaultValue = "100") @Min(1) @Max(1000) int limit,
                @RequestParam(defaultValue = "0") @Min(0) int offset,
                @RequestParam(defaultValue = "jdbc")
                @Pattern(regexp = "(?i)^(jdbc|jpa)$", message = "Debe ser jpa o jdbc") String mode) {
                return pagilaService.get(QueryMode.fromValue(mode)).films(limit, offset);
        }

        @GetMapping("/films/top-rentals")
        @Operation(summary = "Top de películas mas rentadas", description = "Obtiene las películas con mayor cantidad de rentas.")
        public List<FilmRentalDto> topRentals(
                        @RequestParam(defaultValue = "50") @Min(1) @Max(500) int limit,
                        @RequestParam(defaultValue = "jdbc")
                        @Pattern(regexp = "(?i)^(jdbc|jpa)$", message = "Debe ser jpa o jdbc") String mode) {
                return pagilaService.get(QueryMode.fromValue(mode)).topRentals(limit);
        }

        @GetMapping("/customers/top-payments")
        @Operation(summary = "Top de clientes con más pagos", description = "Obtiene los clientes que más pagos han hecho.")
        public List<CustomerPaymentDto> topCustomers(
                        @RequestParam(defaultValue = "50") @Min(1) @Max(500) int limit,
                        @RequestParam(defaultValue = "jdbc")
                        @Pattern(regexp = "(?i)^(jdbc|jpa)$", message = "Debe ser jpa o jdbc") String mode) {
                return pagilaService.get(QueryMode.fromValue(mode)).topCustomers(limit);
        }

        @GetMapping("/films/{filmId}/actors")
        @Operation(summary = "Actores por película", description = "Obtiene los actores que participaron en una película específica.")
        public List<ActorDto> actorsByFilm(
                        @PathVariable @Min(1) int filmId,
                        @RequestParam(defaultValue = "jdbc")
                        @Pattern(regexp = "(?i)^(jdbc|jpa)$", message = "Debe ser jpa o jdbc") String mode) {
                return pagilaService.get(QueryMode.fromValue(mode)).actorsByFilm(filmId);
        }

}
