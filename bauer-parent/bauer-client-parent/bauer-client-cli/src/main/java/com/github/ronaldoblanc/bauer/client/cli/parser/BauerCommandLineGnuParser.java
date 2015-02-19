package com.github.ronaldoblanc.bauer.client.cli.parser;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.github.ronaldoblanc.bauer.client.cli.exception.NotEnoughArgumentsException;

/**
 * Default Gnu parser for the bauer app.
 * 
 * @author Ronaldo Blanc <ronaldoblanc at gmail.com>
 */
public class BauerCommandLineGnuParser {

	private static final CommandLineParser parser = new GnuParser();
	private static CommandLine cmd;

	/**
	 * Parse the app input parameters.
	 * 
	 * @param options
	 * @param args
	 * @throws ParseException
	 * @throws NotEnoughArgumentsException
	 */
	public static void parse(Options options, String[] args)
			throws ParseException, NotEnoughArgumentsException {
		cmd = parser.parse(options, args);
		if (cmd.getOptions().length < 1) {
			throw new NotEnoughArgumentsException();
		}

	}

	/**
	 * Check if some option was informed.
	 * 
	 * @param argument
	 * @return
	 */
	public static boolean hasOption(String argument) {
		return cmd.hasOption(argument);
	}

	/**
	 * Retrieves the argument of an option.
	 * 
	 * @param argument
	 * @param clazz
	 * @return
	 * @throws ParseException
	 */
	public static <T> T getArgument(String option, Class<T> clazz)
			throws ParseException {
		return clazz.cast(cmd.getOptionValue(option));
	}

	public static Option[] getOptions() {
		return cmd.getOptions();
	}
}
