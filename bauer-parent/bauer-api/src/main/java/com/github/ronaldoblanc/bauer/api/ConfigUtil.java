package com.github.ronaldoblanc.bauer.api;

public interface ConfigUtil {

	public String getProperty(String property);

	public <T> T getPropertyAs(Class<T> clazz, String property);
}
