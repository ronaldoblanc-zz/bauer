package com.github.ronaldoblanc.bauer.api;

/**
 * Bauer Context.<br/>
 * Use this context to set/get objects between all layers of your projects.
 * 
 * @author Ronaldo Blanc <ronaldoblanc at gmail.com>
 */
public interface BauerContext {

	/**
	 * Put some object in the context.
	 * 
	 * @param clazz
	 *            The unique key (id) of the object
	 * @param value
	 *            The object itself
	 */
	public abstract void put(Class<?> clazz, Object value);

	/**
	 * Put some object in the context.
	 * 
	 * @param name
	 *            The unique key (id) of the object
	 * @param value
	 *            The object itself
	 */
	public abstract void put(String name, Object value);

	/**
	 * Retrieves an object by its unique key
	 * 
	 * @param clazz
	 *            The unique key (id) of the object
	 * @return The object
	 */
	public abstract Object get(Class<?> clazz);

	/**
	 * Retrieves an object by its unique key
	 * 
	 * @param name
	 *            The unique key (id) of the object
	 * @return The object
	 */
	public abstract Object get(String name);

	/**
	 * Retrieves an object as an instance of the specified class type.<br/>
	 * The class type is also the unique key (id) of the object.
	 * 
	 * @param clazz
	 * @return The object as the required type
	 */
	public abstract <T> T getAs(Class<T> clazz);

	/**
	 * Retrieves an object as an instance of the specified class type.<br/>
	 * The <code>name</code> is the unique key (id) of the object.
	 * 
	 * @param name
	 * @param clazz
	 * @return The object as the required type
	 */
	public abstract <T> T getAs(String name, Class<T> clazz);
}
