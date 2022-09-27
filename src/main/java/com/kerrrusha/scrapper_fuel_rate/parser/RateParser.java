package com.kerrrusha.scrapper_fuel_rate.parser;

import com.kerrrusha.scrapper_fuel_rate.model.GasStationFuelRate;
import com.kerrrusha.scrapper_fuel_rate.parser.concrete.AutoriaRateParser;
import com.kerrrusha.scrapper_fuel_rate.parser.concrete.MinfinRateParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RateParser {
	public static List<GasStationFuelRate> parseConcreteRates(RateParserSource source,
	                                                          GasStationCity sourceCity) throws IOException {
		ParseStrategy parser;
		switch (source) {
			case AUTORIA -> parser = new AutoriaRateParser();
			case MINFIN -> parser = new MinfinRateParser();
			default -> {
				return new ArrayList<>();
			}
		}
		return parser.parse(sourceCity);
	}
}
