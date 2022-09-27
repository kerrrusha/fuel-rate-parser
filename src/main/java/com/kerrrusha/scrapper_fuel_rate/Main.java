package com.kerrrusha.scrapper_fuel_rate;

import com.kerrrusha.scrapper_fuel_rate.model.GasStationFuelRate;
import com.kerrrusha.scrapper_fuel_rate.parser.GasStationCity;
import com.kerrrusha.scrapper_fuel_rate.parser.RateParser;
import com.kerrrusha.scrapper_fuel_rate.parser.RateParserSource;

import java.io.IOException;
import java.util.List;

public class Main {
	public static void main(String[] args) throws IOException {
		List<GasStationFuelRate> ratesAutoria =
				RateParser.parseConcreteRates(RateParserSource.AUTORIA, GasStationCity.KYIV);
		System.out.println(ratesAutoria);

		List<GasStationFuelRate> ratesMinfin =
				RateParser.parseConcreteRates(RateParserSource.MINFIN, GasStationCity.ODESA);
		System.out.println(ratesMinfin);
	}
}
