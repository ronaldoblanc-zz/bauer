package com.github.ronaldoblanc.bauer.api.exception;

/**
 * @author Ronaldo Blanc <ronaldoblanc at gmail.com>
 */
public class InvalidOptionException extends Exception {

	private static final long serialVersionUID = -3203093987647233021L;

	public InvalidOptionException() {
		super();
	}

	public InvalidOptionException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidOptionException(String message) {
		super(message);
	}

	public InvalidOptionException(Throwable cause) {
		super(cause);
	}
}
