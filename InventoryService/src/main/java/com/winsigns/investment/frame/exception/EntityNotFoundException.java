package com.winsigns.investment.frame.exception;

@SuppressWarnings("serial")
public class EntityNotFoundException extends RuntimeException {
	public EntityNotFoundException() {
		super("Requested resource doesn't exist.");
	}
}
