package com.github.ronaldoblanc.bauer.api;

import java.io.IOException;

/**
 * Writes out data.
 * 
 * @author rocharo
 *
 */
public interface DataWritter {

	/**
	 * Writes the <code>data</code> to the destination.
	 * 
	 * @param data
	 * @throws IOException
	 */
	public void write(String data) throws IOException;
}
