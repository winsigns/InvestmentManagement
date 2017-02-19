package com.winsigns.investment.frame.exception;

@SuppressWarnings("serial")
public class AccessDeniedException extends RuntimeException {
	public AccessDeniedException() {
		super("Access denied when requesting the resource.");
	}
}
