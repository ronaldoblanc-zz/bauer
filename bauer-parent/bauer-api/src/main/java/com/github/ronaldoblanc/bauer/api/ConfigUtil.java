package com.github.ronaldoblanc.bauer.api;

/**
 * Utilitary class contract for config.<br/>
 * 
 * @author Ronaldo Blanc <ronaldoblanc at gmail.com>
 */
public interface ConfigUtil {

	public String getProperty(String property);

	public <T> T getPropertyAs(Class<T> clazz, String property);
}
