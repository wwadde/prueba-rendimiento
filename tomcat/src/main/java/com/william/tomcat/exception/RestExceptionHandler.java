package com.william.tomcat.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestControllerAdvice
public class RestExceptionHandler {
	@ExceptionHandler(DatasetNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ProblemDetail handleDatasetNotFound(DatasetNotFoundException ex) {
		return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
	}

	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ProblemDetail handleValidation(ConstraintViolationException ex) {
		return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
	}

	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ProblemDetail handleIllegalArgument(IllegalArgumentException ex) {
		return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ProblemDetail> handleValidation(MethodArgumentNotValidException ex) {
		List<Map<String, String>> violations = ex.getBindingResult()
				.getFieldErrors()
				.stream()
				.map(fe -> Map.of(
						"campo", fe.getField(),
						"mensaje", fe.getDefaultMessage() != null ? fe.getDefaultMessage() : "Valor invalido"
				))
				.toList();

		ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Datos de entrada invalidos");
		pd.setProperty("violations", violations);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(pd);
	}

	@ExceptionHandler(HandlerMethodValidationException.class)
	public ResponseEntity<ProblemDetail> handleValidation(HandlerMethodValidationException ex) {

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

		return ResponseEntity.badRequest().body(pd);
	}
}

