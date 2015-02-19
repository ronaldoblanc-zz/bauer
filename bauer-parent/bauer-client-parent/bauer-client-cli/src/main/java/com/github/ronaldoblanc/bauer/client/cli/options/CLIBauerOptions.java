package com.github.ronaldoblanc.bauer.client.cli.options;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Logger;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import com.github.ronaldoblanc.bauer.client.cli.exception.Messages;
import com.github.ronaldoblanc.bauer.client.cli.parser.OptionsConfData;
import com.github.ronaldoblanc.bauer.core.config.BauerConfigUtil;
import com.github.ronaldoblanc.util.io.file.core.SimpleDelimitedFileDataReader;

/**
 * Reads the core-options.conf and options.conf file and build the options
 * manager for the bauer.
 * 
 * @author Ronaldo Blanc <ronaldoblanc at gmail.com>
 */
public class CLIBauerOptions {
	private static final String CORE_OPTIONS = "core-options.conf";
	private static final String DATABASE_CORE_OPTIONS = "db-core-options.conf";
	private static final String OPTIONS = "options.conf";
	private static final Logger LOGGER = Logger
			.getLogger(CLIBauerOptions.class.getCanonicalName());
	private static final Map<String, String> overrideOptions = new HashMap<String, String>();
	private static final Map<String, String> dependencyTreeConf = new HashMap<String, String>();
	private static final Map<Option, Option> dependencyTree = new HashMap<Option, Option>();
	private static final CLIBauerOptions INSTANCE = new CLIBauerOptions();

	private final Options options = new Options();

	private CLIBauerOptions() {
		LOGGER.finer(CLIBauerOptions.class.getCanonicalName() + " instanced");
		try {
			// Add options from core-options.conf, db-core-options.conf and
			// options.conf file
			String[] optionsConfs;
			Boolean database = BauerConfigUtil.getInstance().getPropertyAs(
					Boolean.class, BauerConfigUtil.DATABASE);
			if (database) {
				optionsConfs = new String[] { CORE_OPTIONS,
						DATABASE_CORE_OPTIONS, OPTIONS };
			} else {
				optionsConfs = new String[] { CORE_OPTIONS, OPTIONS };
			}
			for (String optionFile : optionsConfs) {
				for (Map<String, String> option : buildOptions(optionFile)) {
					if (!options.hasOption(option.get(OptionsConfData.SHORT_OPTION
							.name()))) {
						Option opt = new Option(
								option.get(OptionsConfData.SHORT_OPTION.name()),
								option.get(OptionsConfData.LONG_OPTION.name()),
								Boolean.valueOf(option.get(OptionsConfData.HAS_ARG
										.name())),
								option.get(OptionsConfData.DESCRIPTION.name()));
						opt.setRequired(Boolean.valueOf(option
								.get(OptionsConfData.MANDATORY.name())));
						String dep = option.get(OptionsConfData.DEPENDENT.name());
						if (dep != null && !dep.isEmpty()) {
							dependencyTreeConf.put(opt.getOpt(), dep);
							LOGGER.finest("Dependency option detected: [" + opt
									+ ", " + dep + "]");
						}

						String override = option.get(OptionsConfData.OVERRIDE
								.name());
						if (override != null && !override.isEmpty()) {
							overrideOptions.put(opt.getOpt(), override);
						}

						if (opt.getOpt() != null) {
							options.addOption(opt);
							LOGGER.finest("Added option: [" + opt + "]");
						} else {
							throw new RuntimeException("Please check if "
									+ optionFile + " has all needed data.");
						}
					} else {
						LOGGER.warning(String
								.format(Messages.DUPLICATED_OPTION));
					}
				}
			}

			for (Entry<String, String> depEntry : dependencyTreeConf.entrySet()) {
				String dependent = depEntry.getKey();
				String dependency = depEntry.getValue();
				if (options.hasOption(dependent)
						&& options.hasOption(dependency)) {
					dependencyTree.put(options.getOption(dependent),
							options.getOption(dependency));
					LOGGER.finest("Dependency option added: ["
							+ options.getOption(dependent) + ", "
							+ options.getOption(dependency) + "]");

				} else {
					throw new RuntimeException(
							"Please check your dependency tree configuration.");
				}

			}
		} catch (Exception e) {
			throw new RuntimeException("Please check if " + OPTIONS
					+ " file is accesible", e);
		}
	}

	public static final CLIBauerOptions getInstance() {
		return INSTANCE;
	}

	public boolean isOverriding(String opt) {
		return overrideOptions.containsKey(opt);
	}

	public String getOverridingOption(String opt) {
		return overrideOptions.get(opt);
	}

	public boolean isDependent(String opt) {
		Option option = options.getOption(opt);
		return dependencyTree.containsKey(option);
	}

	public boolean isDependency(String opt) {
		Option option = options.getOption(opt);
		return dependencyTree.containsValue(option);
	}

	public Option getDependencyOption(String opt) {
		Option option = options.getOption(opt);
		return dependencyTree.get(option);
	}

	/**
	 * Returns the options accepted by this bauer.
	 * 
	 * @return
	 */
	public Options getOptions() {
		return options;
	}

	/**
	 * Returns the option by the option name.
	 * 
	 * @param opt
	 * @return
	 */
	public Option getOption(String opt) {
		return options.getOption(opt);
	}

	private List<Map<String, String>> buildOptions(String optionsFile)
			throws IOException {
		InputStream is = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream(optionsFile);
		LOGGER.finest("Options file: [" + optionsFile + "]");
		List<Map<String, String>> options = SimpleDelimitedFileDataReader
				.getInstance().getDataAsMap(is, OptionsConfData.class, null, false);
		return options;
	}

	public Set<Option> getDependents() {
		return dependencyTree.keySet();
	}

	public Set<String> getOverridables() {
		return overrideOptions.keySet();
	}

}
