package com.kerrrusha.scrapper_fuel_rate.parser;

import com.kerrrusha.scrapper_fuel_rate.model.GasStationFuelRate;

import java.io.IOException;
import java.util.List;

public interface ParseStrategy {
	List<GasStationFuelRate> parse(GasStationCity sourceCity) throws IOException;
}
