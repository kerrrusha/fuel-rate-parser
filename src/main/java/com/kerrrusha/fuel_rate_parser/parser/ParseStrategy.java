package com.kerrrusha.fuel_rate_parser.parser;

import com.kerrrusha.fuel_rate_parser.model.GasStationFuelRate;

import java.io.IOException;
import java.util.List;

public interface ParseStrategy {
	List<GasStationFuelRate> parse(GasStationCity sourceCity) throws IOException;
}
