package com.kerrrusha.scrapper_fuel_rate.presentation.cli.commands;

import com.kerrrusha.scrapper_fuel_rate.parser.GasStationCity;
import com.kerrrusha.scrapper_fuel_rate.presentation.cli.OutputUtil;
import picocli.CommandLine;

@CommandLine.Command(
		name = "cities",
		description = "list of available cities",
		mixinStandardHelpOptions = true
)

public class CitiesCommand implements Runnable {
	@CommandLine.Option(names = {"--help", "help"}, usageHelp = true, description = "display this help and exit")
	boolean help;

	@Override
	public void run() {
		OutputUtil.println(GasStationCity.valuesString());
	}
}
