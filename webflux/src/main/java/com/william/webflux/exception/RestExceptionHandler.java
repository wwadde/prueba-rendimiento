package com.william.webflux.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestControllerAdvice
public class RestExceptionHandler {
	@ExceptionHandler(DatasetNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public Mono<ProblemDetail> handleDatasetNotFound(DatasetNotFoundException ex) {
		return Mono.just(ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage()));
	}

	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Mono<ProblemDetail> handleValidation(ConstraintViolationException ex) {
		return Mono.just(ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage()));
	}

	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Mono<ProblemDetail> handleIllegalArgument(IllegalArgumentException ex) {
		return Mono.just(ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage()));
	}

	@ExceptionHandler(HandlerMethodValidationException.class)
	public Mono<ProblemDetail> handleValidation(HandlerMethodValidationException ex) {
		var violations = ex.getParameterValidationResults().stream()
				.map(result -> Map.of(
						"campo", Objects.requireNonNullElse(
								result.getMethodParameter().getParameterName(),
								"desconocido"),
						"mensaje", Objects.requireNonNullElse(
								result.getResolvableErrors().getFirst().getDefaultMessage(),
								"Valor inválido")
				))
				.toList();

		ProblemDetail pd = ProblemDetail.forStatusAndDetail(
				HttpStatus.BAD_REQUEST,
				"Datos de entrada inválidos");

		pd.setProperty("violations", violations);

		return Mono.just(pd);
	}
}

