package com.github.ronaldoblanc.bots.api;

/**
 * The filename could not be found or access is denied.
 * 
 * @author rocharo
 * @since 2014-07-03
 */
public class NoSuchFileException extends Exception {

	private static final long serialVersionUID = 6072111253299035120L;

	/**
	 * @param filename
	 */
	public NoSuchFileException(String filename) {
		super(filename);
	}

	/**
	 * @param filename
	 * @param cause
	 */
	public NoSuchFileException(String filename, Throwable cause) {
		super(filename, cause);
	}
}
