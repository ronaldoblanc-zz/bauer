package com.github.ronaldoblanc.bauer.client.cli.exception;

/**
 * @author Ronaldo Blanc <ronaldoblanc at gmail.com>
 */
public class NotEnoughArgumentsException extends Exception {

	private static final long serialVersionUID = -4537341059004744744L;

	public NotEnoughArgumentsException() {
	}

	public NotEnoughArgumentsException(String arg0) {
		super(arg0);
	}

	public NotEnoughArgumentsException(Throwable arg0) {
		super(arg0);
	}

	public NotEnoughArgumentsException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}
}
