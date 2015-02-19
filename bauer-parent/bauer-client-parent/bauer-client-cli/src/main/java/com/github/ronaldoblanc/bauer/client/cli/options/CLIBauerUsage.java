package com.github.ronaldoblanc.bauer.client.cli.options;

import org.apache.commons.cli.HelpFormatter;

import com.github.ronaldoblanc.bauer.core.config.BauerConfigUtil;

/**
 * Prints out the usage for this bauer.
 * 
 * @author Ronaldo Blanc <ronaldoblanc at gmail.com>
 */
public class CLIBauerUsage {
	private static final HelpFormatter FORMATTER = new HelpFormatter();

	/**
	 * Prints out the usage.
	 * 
	 * @param options
	 */
	public static void printUsage() {
		FORMATTER.printHelp(
				BauerConfigUtil.getInstance().getProperty(
						BauerConfigUtil.APP_NAME), CLIBauerOptions
						.getInstance().getOptions());
	}
}
