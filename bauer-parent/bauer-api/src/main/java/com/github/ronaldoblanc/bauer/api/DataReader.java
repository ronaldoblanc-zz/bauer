package com.github.ronaldoblanc.bauer.api;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * Reads a delimited data record using the <code>clazz</code> parameter
 * <code>Enum</code> as the record fields definition.<br/>
 * The resulting <code>Map</code> has the <code>Enum</code> field name as the
 * key and the correlated data on the input file as the value.
 * 
 * @author Ronaldo Blanc <ronaldoblanc at gmail.com>
 */
public interface DataReader {

	/**
	 * Reads the data as a <code>java.util.List</code> of
	 * <code>java.util.Map</code> representing all the records in the file.
	 * 
	 * @param is
	 * @param clazz
	 * @param delimiter
	 * @param hasHeader
	 * @return A list of records represented by a <code>Map</code>.
	 * @throws IOException
	 */
	@SuppressWarnings("rawtypes")
	public abstract List<Map<String, String>> getDataAsMap(InputStream is,
			Class<? extends Enum> clazz, String delimiter, boolean hasHeader)
			throws IOException;

}
