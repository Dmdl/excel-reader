package com.meetplanner.exception;

public class GenricSqlException extends Exception {

	private static final long serialVersionUID = 1L;

	public GenricSqlException() {
		super();
	}

	public GenricSqlException(String message) {
		super(message);
	}

	public GenricSqlException(String message, Throwable cause) {
		super(message, cause);
	}

	public GenricSqlException(Throwable cause) {
		super(cause);
	}

}
