package com.flow.railwayservice.exception;

public class BadRequestException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public BadRequestException(){}
	public BadRequestException(String message) { super(message); }
}
