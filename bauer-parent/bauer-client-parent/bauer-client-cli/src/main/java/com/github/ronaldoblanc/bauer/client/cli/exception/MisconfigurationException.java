package com.github.ronaldoblanc.bauer.client.cli.exception;

/**
 * @author Ronaldo Blanc <ronaldoblanc at gmail.com>
 */
public class MisconfigurationException extends Exception {

	private static final long serialVersionUID = -3640784094057659198L;

	public MisconfigurationException() {
		super();
	}

	public MisconfigurationException(String message, Throwable cause) {
		super(message, cause);
	}

	public MisconfigurationException(String message) {
		super(message);
	}

	public MisconfigurationException(Throwable cause) {
		super(cause);
	}
}
