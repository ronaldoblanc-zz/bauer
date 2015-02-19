package com.github.ronaldoblanc.bauer.core.options;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @author Ronaldo Blanc <ronaldoblanc at gmail.com>
 */
public final class BauerOptions {

	private static Map<String, Object> options = new HashMap<String, Object>();
	private static final String OPTION_ADDED = "New option added: [%s: %s]";
	private static final String OPTION_RETRIEVED = "A option was retrieved: [%s]";
	private static BauerOptions INSTANCE = new BauerOptions();
	private static final Logger LOGGER = Logger.getLogger(BauerOptions.class
			.getCanonicalName());

	private BauerOptions() {
	}

	public static BauerOptions getInstance() {
		LOGGER.finer(BauerOptions.class.getCanonicalName() + " instanced");
		return INSTANCE;
	}

	public void addOption(String name, Object value) {
		LOGGER.finest(String.format(OPTION_ADDED, name, value));
		options.put(name, value);
	}

	public <T> T getOption(String name, Class<T> type) {
		LOGGER.finest(String.format(OPTION_RETRIEVED, name));
		if (options.isEmpty()) {
			throw new IllegalStateException("No options were added.");
		}
		return type.cast(options.get(name));
	}

	public <T> T getOption(String name, Class<T> type, Object defaultValue) {
		LOGGER.finest(String.format(OPTION_RETRIEVED, name));
		if (options.isEmpty()) {
			throw new IllegalStateException("No options were added.");
		}
		Object result = options.get(name);
		if (result == null) {
			return type.cast(defaultValue);
		}
		return type.cast(result);
	}

	public boolean hasOption(String name) {
		if (options.isEmpty()) {
			throw new IllegalStateException("No options were added.");
		}

		return options.containsKey(name);
	}

}
