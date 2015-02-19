package com.github.ronaldoblanc.bauer.core.config;

import com.github.ronaldoblanc.util.config.ConfigUtil;

public final class BauerConfigUtil {
	private static final String DEFAULT_CONFIG_FILE = "bauer.properties";
	private static final String DEFAULT_APP_NAME = "bauer-app";
	public static final String APP_NAME = "APP_NAME";
	public static final String DATABASE = "DATABASE";

	private static final ConfigUtil config = ConfigUtil
			.getInstance(DEFAULT_CONFIG_FILE);
	private static final BauerConfigUtil INSTANCE = new BauerConfigUtil();

	private BauerConfigUtil() {
		fixProperties();
	}

	public static BauerConfigUtil getInstance() {
		return INSTANCE;
	}

	public Boolean getPropertyAs(Class<Boolean> clazz, String key) {
		return config.getPropertyAs(clazz, key);
	}

	private void fixProperties() {
		if (!config.containsKey(APP_NAME)) {
			config.putProperty(APP_NAME, DEFAULT_APP_NAME);
		}

		if (!config.containsKey(DATABASE)) {
			config.putProperty(DATABASE, false);
		}
	}

	public String getProperty(String key) {
		return config.getProperty(key);
	}

}
