package com.kerrrusha.fuel_rate_parser.presentation.cli.commands;

import com.kerrrusha.fuel_rate_parser.model.GasStationFuelRate;
import com.kerrrusha.fuel_rate_parser.parser.GasStationCity;
import com.kerrrusha.fuel_rate_parser.parser.RateParser;
import com.kerrrusha.fuel_rate_parser.parser.RateParserSource;
import com.kerrrusha.fuel_rate_parser.parser.concrete.AutoriaRateParser;
import com.kerrrusha.fuel_rate_parser.tools.OutputUtil;
import com.kerrrusha.fuel_rate_parser.tools.RatesToTablePreparingUtil;
import dnl.utils.text.table.TextTable;
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
		GasStationCity _city = GasStationCity.CHERNIHIV;
		RateParserSource _source = RateParserSource.AUTORIA;

		logger.info("Providing rates from " + _source + "for " + _city + " city");

		List<GasStationFuelRate> rates;
		try {
			rates = RateParser.parseConcreteRates(_source, _city);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		OutputUtil.println("Rates from " + _source + " for " + _city + " city:");

		String[] columnNames = RatesToTablePreparingUtil.prepareHeader().toArray(new String[0]);
		String[][] data = RatesToTablePreparingUtil.prepareData(rates);

		TextTable tt = new TextTable(columnNames, data);
		tt.printTable(OutputUtil.getPrintStream(), 0);
	}
}
