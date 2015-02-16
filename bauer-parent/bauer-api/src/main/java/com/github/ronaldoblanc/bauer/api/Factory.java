package com.github.ronaldoblanc.bauer.api;

/**
 * @author Ronaldo Blanc <ronaldoblanc at gmail.com>
 */
public interface Factory<T> {

	T newInstance();
}
