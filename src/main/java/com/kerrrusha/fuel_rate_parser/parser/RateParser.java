package com.kerrrusha.fuel_rate_parser.parser;

import com.kerrrusha.fuel_rate_parser.model.GasStationFuelRate;
import com.kerrrusha.fuel_rate_parser.parser.concrete.AutoriaRateParser;
import com.kerrrusha.fuel_rate_parser.parser.concrete.MinfinRateParser;

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
