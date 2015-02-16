package com.github.ronaldoblanc.bauer.api;

import java.io.IOException;

/**
 * Writes data on multiple destinations.<br/>
 * Very useful in loops that generates data for different outputs.<br/>
 * You must call <code>newWritter()</code> method, before writes data out.<br/>
 * 
 * @author Ronaldo Blanc <ronaldoblanc at gmail.com>
 */
public interface MultiDataWriter {

	/**
	 * Defines a new writer.
	 * 
	 * @param filename
	 * @throws NoSuchFileException
	 */
	public abstract void newWriter(String outputName)
			throws NoSuchFileException;

	/**
	 * Checks if a writer exists.
	 * 
	 * @param filename
	 * @return
	 */
	public abstract boolean hasWriter(String outputName);

	/**
	 * Writes out data.
	 * 
	 * @param outputName
	 *            The destination to writes on.
	 * @param data
	 *            The data that should be written.
	 * @param noDups
	 *            If <code>true</code> duplicate records are not allowed.
	 * @throws NoSuchFileException
	 * @throws IOException
	 */
	public abstract void write(String outputName, String data, boolean noDups)
			throws NoSuchFileException, IOException;

}
