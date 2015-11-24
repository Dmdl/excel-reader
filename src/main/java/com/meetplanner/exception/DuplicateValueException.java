package com.meetplanner.exception;

public class DuplicateValueException extends Exception {

	private static final long serialVersionUID = 1L;

	public DuplicateValueException() {
		super();
	}

	public DuplicateValueException(String message) {
		super(message);
	}

	public DuplicateValueException(String message, Throwable cause) {
		super(message, cause);
	}

	public DuplicateValueException(Throwable cause) {
		super(cause);
	}

}
