package com.github.ronaldoblanc.bauer.client.cli.options;

import java.util.Arrays;
import java.util.Set;
import java.util.logging.Logger;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.ParseException;

import com.github.ronaldoblanc.bauer.client.cli.exception.InvalidArgumentException;
import com.github.ronaldoblanc.bauer.client.cli.exception.NotEnoughArgumentsException;
import com.github.ronaldoblanc.bauer.client.cli.parser.BauerCommandLineGnuParser;
import com.github.ronaldoblanc.bauer.core.config.BauerConfigUtil;
import com.github.ronaldoblanc.bauer.core.options.BauerOptions;
import com.github.ronaldoblanc.bauer.core.options.DefaultBauerOptions;

/**
 * Parses command line args options and build the <code>BauerOptions</code>
 * object.
 * 
 * @author Ronaldo Blanc <ronaldoblanc at gmail.com>
 */
public class CLIBauerOptionsBuilder {
	private static final BauerOptions bauerOptions = BauerOptions.getInstance();
	private static final Logger LOGGER = Logger
			.getLogger(CLIBauerOptionsBuilder.class.getCanonicalName());

	private CLIBauerOptionsBuilder() {
		LOGGER.finer(CLIBauerOptionsBuilder.class.getCanonicalName()
				+ " instanced");
	}

	public static BauerOptions build(String[] args)
			throws InvalidArgumentException, NotEnoughArgumentsException,
			ParseException {
		parse(args);

		for (Option option : BauerCommandLineGnuParser.getOptions()) {
			Object value = null;
			if (option.hasArg()) {
				value = BauerCommandLineGnuParser.getArgument(option.getOpt(),
						String.class);
			} else {
				value = BauerCommandLineGnuParser.hasOption(option.getOpt());
			}
			bauerOptions.addOption(option.getOpt(), value);
			bauerOptions.addOption(option.getLongOpt(), value);
			LOGGER.finest("Option added: [" + option + ", " + value + "]");
		}
		Boolean database = BauerConfigUtil.getInstance().getPropertyAs(
				Boolean.class, BauerConfigUtil.DATABASE);
		if (database) {
			sanityCheck();
		}
		return bauerOptions;
	}

	private static void sanityCheck() throws NotEnoughArgumentsException,
			ParseException {
		if (getJdbcPropertiesArg() == null && getXmlDataSourceArg() == null) {
			Option jdbcPropertiesFile = getJdbcPropetiesOpt();
			Option xmlDataSource = getXmlDataSourceOpt();
			throw new NotEnoughArgumentsException("Missing ["
					+ jdbcPropertiesFile.getLongOpt() + "] or ["
					+ xmlDataSource.getLongOpt() + "] files");
		}
	}

	private static Option getJdbcPropetiesOpt() throws ParseException {
		return CLIBauerOptions.getInstance().getOption(
				DefaultBauerOptions.JDBC_PROPERTIES_DATA_SOURCE_FILE
						.getOption());
	}

	private static String getJdbcPropertiesArg() throws ParseException {
		return BauerCommandLineGnuParser.getArgument(
				DefaultBauerOptions.JDBC_PROPERTIES_DATA_SOURCE_FILE
						.getOption(), String.class);
	}

	private static Option getXmlDataSourceOpt() throws ParseException {
		return CLIBauerOptions.getInstance().getOption(
				DefaultBauerOptions.SQL_DEVELOPER_XML_DATA_SOURCE_FILE
						.getOption());
	}

	private static String getXmlDataSourceArg() throws ParseException {
		return BauerCommandLineGnuParser.getArgument(
				DefaultBauerOptions.SQL_DEVELOPER_XML_DATA_SOURCE_FILE
						.getOption(), String.class);
	}

	private static void parse(String[] args) throws InvalidArgumentException,
			NotEnoughArgumentsException {
		try {
			LOGGER.finest("Args to parse: [" + Arrays.asList(args) + "]");
			for (String overridable : CLIBauerOptions.getInstance()
					.getOverridables()) {
				for (String arg : args) {
					String option = "-" + overridable;
					if (arg.equals(option)) {
						int pos = args.length;
						String overridableOption = "-"
								+ CLIBauerOptions.getInstance()
										.getOverridingOption(overridable);
						args = Arrays.copyOf(args, pos + 2);
						args[pos] = overridableOption;
						args[pos + 1] = "overridable-option";

					}
				}
			}
			BauerCommandLineGnuParser.parse(CLIBauerOptions.getInstance()
					.getOptions(), args);
			Set<Option> dependentOptions = CLIBauerOptions.getInstance()
					.getDependents();
			for (Option dependent : dependentOptions) {
				Option dependencyOption = CLIBauerOptions.getInstance()
						.getDependencyOption(dependent.getOpt());
				boolean hasDependent = BauerCommandLineGnuParser
						.hasOption(dependent.getOpt());
				boolean hasDependency = BauerCommandLineGnuParser
						.hasOption(dependencyOption.getOpt());
				if ((hasDependent && !hasDependency)) {
					throw new NotEnoughArgumentsException("Option: ["
							+ dependent + "] relies on [" + dependencyOption
							+ "]");
				}
			}
		} catch (NullPointerException npe) {
			throw new InvalidArgumentException(npe);
		} catch (ParseException pe) {
			throw new NotEnoughArgumentsException(pe);
		}
	}
}
