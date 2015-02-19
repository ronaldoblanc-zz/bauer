package com.github.ronaldoblanc.bauer.core.options;

public enum DefaultBauerOptions {
	CONFIG_FILE("c"),
	JDBC_PROPERTIES_DATA_SOURCE_FILE("j"),
	SQL_DEVELOPER_XML_DATA_SOURCE_FILE("x"),
	SQL_DEVELOPER_XML_DATA_SOURCE_NAME("D"),
	VERBOSE("v");

	String opt;

	private DefaultBauerOptions(String option) {
		opt = option;
	}

	public String getOption() {
		return opt;
	}
}
