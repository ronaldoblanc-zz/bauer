package com.github.ronaldoblanc.bauer.api.exception;

/**
 * @author Ronaldo Blanc <ronaldoblanc at gmail.com>
 */
public interface CoreMessages {
	String SQL_DEVELOPER_XML_MULTIPLE_DB = "Sql Developer XML (-x) [%s] has more than one data source and no data-source (-D) was provided.";
	String SQL_DEVELOPER_XML_NO_DATABASE_FOUND = "Sql Developer XML (-x) the requested data source does not exit in the provided file [%s]";
}
