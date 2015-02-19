package com.github.ronaldoblanc.bauer.core.util;

import java.util.Properties;
import java.util.logging.Logger;

import com.github.ronaldoblanc.util.config.exception.InvalidConfigurationException;
import com.github.ronaldoblanc.util.io.file.api.NoSuchFileException;
import com.github.ronaldoblanc.util.spring.SpringUtil;

/**
 * Note: You better check the <code>DataSourceConfigUtil</code> class'<br/>
 * javadoc.
 * 
 * @author Ronaldo Blanc <ronaldoblanc at gmail.com>
 */
public final class Bauer {
	private static final Logger LOGGER = Logger.getLogger(Bauer.class
			.getCanonicalName());
	private static final Bauer INSTANCE = new Bauer();

	private Bauer() {
	}

	public static Bauer getInstance() {
		return INSTANCE;
	}

	public void springStartUp(String applicationContextXml)
			throws NoSuchFileException, InvalidConfigurationException {
		Properties dataSourceConfig = DataSourceConfigUtil.getInstance()
				.retrieveDataSourceConfig();
		if (dataSourceConfig == null) {
			throw new RuntimeException("No data source config found in context");
		}

		LOGGER.finest("Data source config: "
				+ dataSourceConfig.getProperty("jdbc.username") + "@["
				+ dataSourceConfig.getProperty("jdbc.url") + "]");

		SpringUtil.getInstance().startApp(applicationContextXml,
				dataSourceConfig);
	}
}
