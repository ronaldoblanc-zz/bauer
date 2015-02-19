package com.github.ronaldoblanc.bauer.client.cli;

import org.apache.commons.cli.ParseException;

import com.github.ronaldoblanc.bauer.client.cli.exception.InvalidArgumentException;
import com.github.ronaldoblanc.bauer.client.cli.exception.NotEnoughArgumentsException;
import com.github.ronaldoblanc.bauer.client.cli.options.CLIBauerOptionsBuilder;
import com.github.ronaldoblanc.bauer.client.cli.options.CLIBauerUsage;
import com.github.ronaldoblanc.bauer.core.options.BauerOptions;
import com.github.ronaldoblanc.bauer.core.util.Bauer;
import com.github.ronaldoblanc.util.config.exception.InvalidConfigurationException;
import com.github.ronaldoblanc.util.io.file.api.NoSuchFileException;

public final class CLIBauer {

	private static final CLIBauer INSTANCE = new CLIBauer();

	private CLIBauer() {
	}

	public static CLIBauer getInstance() {
		return INSTANCE;
	}

	public BauerOptions init(String[] args) throws Exception {
		Exception toThrow = null;
		try {
			return CLIBauerOptionsBuilder.build(args);
		} catch (InvalidArgumentException e) {
			toThrow = e;
		} catch (NotEnoughArgumentsException e) {
			toThrow = e;
		} catch (ParseException e) {
			toThrow = e;
		}

		CLIBauerUsage.printUsage();
		throw toThrow;
	}

	public void springStartUp(String applicationContextXml)
			throws NoSuchFileException, InvalidConfigurationException {
		Bauer.getInstance().springStartUp(applicationContextXml);
	}
}
