package com.william.webflux.exception;

public class DatasetNotFoundException extends RuntimeException {
	public DatasetNotFoundException(String dataset) {
		super("Dataset not found: " + dataset);
	}
}

