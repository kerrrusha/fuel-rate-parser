package com.kerrrusha.fuel_rate_parser.presentation.cli.commands;

import picocli.CommandLine;

@CommandLine.Command(
		name = "fuel-rate-parser",
		description = "Provides a list of fuel prices for the specified city. Able to parse data from different sources.",
		mixinStandardHelpOptions = true,
		subcommands = {
				RatesCommand.class,
				CitiesCommand.class
		}
)

public class MainCommand implements Runnable {
	@CommandLine.Option(names = {"--help", "help"}, usageHelp = true, description = "display this help and exit")
	boolean help;

	@Override
	public void run() {}
}
