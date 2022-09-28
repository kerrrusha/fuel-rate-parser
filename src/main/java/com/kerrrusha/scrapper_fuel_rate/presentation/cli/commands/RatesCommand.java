package com.kerrrusha.scrapper_fuel_rate.presentation.cli.commands;

import com.kerrrusha.scrapper_fuel_rate.model.GasStationFuelRate;
import com.kerrrusha.scrapper_fuel_rate.parser.GasStationCity;
import com.kerrrusha.scrapper_fuel_rate.parser.RateParser;
import com.kerrrusha.scrapper_fuel_rate.parser.RateParserSource;
import com.kerrrusha.scrapper_fuel_rate.parser.concrete.AutoriaRateParser;
import com.kerrrusha.scrapper_fuel_rate.presentation.cli.OutputUtil;
import org.apache.log4j.Logger;
import picocli.CommandLine;

import java.io.IOException;
import java.util.List;

@CommandLine.Command(
		name = "rates",
		description = "get fuel rates",
		mixinStandardHelpOptions = true
)

public class RatesCommand implements Runnable {
	private final static Logger logger = Logger.getLogger(AutoriaRateParser.class);

	@CommandLine.Option(names = {"-c", "--city"}, description = "choose city to get fuel rates for")
	GasStationCity city;

	@CommandLine.Option(names = {"--help", "help"}, usageHelp = true, description = "display this help and exit")
	boolean help;

	@Override
	public void run() {
		logger.info("Providing rates for " + city + " city");

		List<GasStationFuelRate> rates;
		try {
			rates = RateParser.parseConcreteRates(RateParserSource.AUTORIA, GasStationCity.CHERNIHIV);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		OutputUtil.println(rates.toString());
	}
}
