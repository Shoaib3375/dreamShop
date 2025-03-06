package org.example.dreamshop.exception;

public class productNotFoundException extends RuntimeException {
	public productNotFoundException(String message) {
		super(message);
	}
}
