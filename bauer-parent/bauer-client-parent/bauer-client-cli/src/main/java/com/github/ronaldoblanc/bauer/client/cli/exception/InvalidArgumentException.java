package com.github.ronaldoblanc.bauer.client.cli.exception;

/**
 * @author Ronaldo Blanc <ronaldoblanc at gmail.com>
 */
public class InvalidArgumentException extends Exception {

	private static final long serialVersionUID = -4862411317180579350L;

	public InvalidArgumentException() {
		super();
	}

	public InvalidArgumentException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidArgumentException(String message) {
		super(message);
	}

	public InvalidArgumentException(Throwable cause) {
		super(cause);
	}
}
