package com.github.ronaldoblanc.bauer.core.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

import com.github.ronaldoblanc.bauer.api.exception.CoreMessages;
import com.github.ronaldoblanc.bauer.api.exception.InvalidOptionException;
import com.github.ronaldoblanc.bauer.core.options.BauerOptions;
import com.github.ronaldoblanc.bauer.core.options.DefaultBauerOptions;
import com.github.ronaldoblanc.util.ConnectionProperties;
import com.github.ronaldoblanc.util.SqlDeveloperXmlParser;
import com.github.ronaldoblanc.util.config.exception.InvalidConfigurationException;
import com.github.ronaldoblanc.util.context.ContextImpl;
import com.github.ronaldoblanc.util.io.file.api.NoSuchFileException;

/**
 * Bauer data source configuration utility class.<br/>
 * Based on the configuration options from <code>BauerContext</code>,<br/>
 * it loads the database configuration into <code>BauerContext</code>.
 * 
 * @author Ronaldo Blanc <ronaldoblanc at gmail.com>
 */
public final class DataSourceConfigUtil {
	public static final String JDBC_URL = "jdbc.url";
	public static final String JDBC_DRIVERCLASSNAME = "jdbc.driverClassName";
	public static final String JDBC_USERNAME = "jdbc.username";
	public static final String JDBC_PASSWORD = "jdbc.password";

	private static final int XML_DB_ENTRIES_ALLOWED = 1;
	private static final String XML_DATA_SOURCE = "Datasource from XML: [%s]";
	private static final String JDBC_PROP_DATA_SOURCE = "Datasource from prop: [%s]";
	private static final DataSourceConfigUtil INSTANCE = new DataSourceConfigUtil();
	private static final Logger LOGGER = Logger
			.getLogger(DataSourceConfigUtil.class.getCanonicalName());

	private static final BauerOptions bauerOptions = BauerOptions.getInstance();

	/**
	 * Default data source config.
	 */
	public static final String DATA_SOURCE_CONFIG = "dataSourceConfig";

	private DataSourceConfigUtil() {
	}

	public static DataSourceConfigUtil getInstance() {
		LOGGER.finer(DataSourceConfigUtil.class.getCanonicalName()
				+ " instanced");
		return INSTANCE;
	}

	/**
	 * Retrieves the data source configuration from SQL Developer XML or
	 * <code>jdbc.properties</code> and add it to the <code>BauerContext</code>.<br/>
	 * * ***IMPORTANT***<br/>
	 * This API does not support multiple data sources yet.
	 * 
	 * @return The connection <code>Properties</code>
	 * @throws NoSuchFileException
	 * @throws InvalidConfigurationException
	 */
	public Properties retrieveDataSourceConfig() throws NoSuchFileException,
			InvalidConfigurationException {

		Properties dataSourceConfig;
		String propertiesFileName = "no-file";
		try {
			// XML Data Source Config
			if (isXmlDataSourceFlag()) {
				LOGGER.finest(String
						.format(XML_DATA_SOURCE, getXmlDataSource()));
				final ConnectionProperties connectionProperties = loadConnectionProperties();
				dataSourceConfig = connectionProperties.asProperties();
				LOGGER.info(connectionProperties.toString());
			} else {
				// Properties Data Source Config
				dataSourceConfig = new Properties();
				propertiesFileName = getJdbcPropertiesFile();
				if (propertiesFileName == null) {
					throw new InvalidConfigurationException(
							"No datasource config file was informed.");
				}
				LOGGER.finest(String.format(JDBC_PROP_DATA_SOURCE,
						propertiesFileName));
				dataSourceConfig.load(new FileInputStream(propertiesFileName));
				LOGGER.info("Connection url: ["
						+ dataSourceConfig.getProperty(JDBC_URL) + "]");
			}
			ContextImpl.getInstance().put(DATA_SOURCE_CONFIG, dataSourceConfig);
		} catch (FileNotFoundException fnfe) {
			throw new NoSuchFileException(propertiesFileName);
		} catch (IOException e) {
			throw new InvalidConfigurationException(e);
		}
		return dataSourceConfig;
	}

	/**
	 * Loads the data source configuration from SQL Developer XML.
	 * 
	 * @return The <code>ConnectionProperties</code>
	 * @throws InvalidConfigurationException
	 * @throws NotEnoughArgumentsException
	 * @throws MisconfigurationException
	 */
	private ConnectionProperties loadConnectionProperties()
			throws InvalidConfigurationException {
		final SqlDeveloperXmlParser sdxp = new SqlDeveloperXmlParser();
		ConnectionProperties result;
		try {
			final Map<String, ConnectionProperties> connections = sdxp
					.getConnections(getXmlDataSource());
			// Multiple database in the xml, but no one was requested
			if (connections.size() > XML_DB_ENTRIES_ALLOWED
					&& !isDataSourceFlag()) {
				throw new InvalidOptionException(String.format(
						CoreMessages.SQL_DEVELOPER_XML_MULTIPLE_DB,
						getXmlDataSource()));
			}

			// There is only one database
			if (connections.size() == XML_DB_ENTRIES_ALLOWED) {
				result = connections.get(connections.keySet().toArray()[0]);
			} else {// retrieve the requested database
				result = connections.get(getDataSource());
			}

			if (result == null) {
				throw new InvalidConfigurationException(String.format(
						CoreMessages.SQL_DEVELOPER_XML_NO_DATABASE_FOUND,
						getXmlDataSource()));
			}

			return result;
		} catch (Exception e) {
			throw new InvalidConfigurationException(e);
		}
	}

	private String getJdbcPropertiesFile() {
		return bauerOptions.getOption(
				DefaultBauerOptions.JDBC_PROPERTIES_DATA_SOURCE_FILE
						.getOption(), String.class);
	}

	private boolean isXmlDataSourceFlag() {
		return getXmlDataSource() != null;
	}

	private String getXmlDataSource() {
		return bauerOptions.getOption(
				DefaultBauerOptions.SQL_DEVELOPER_XML_DATA_SOURCE_FILE
						.getOption(), String.class);
	}

	private boolean isDataSourceFlag() {
		return getDataSource() != null;
	}

	private String getDataSource() {
		return bauerOptions.getOption(
				DefaultBauerOptions.SQL_DEVELOPER_XML_DATA_SOURCE_NAME
						.getOption(), String.class);
	}

}
