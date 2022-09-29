package com.kerrrusha.fuel_rate_parser.model;

import java.util.HashMap;
import java.util.Map;

public class GasStationFuelRate {
	private final String gasStationName;
	private final Map<FuelName, Double> fuelToPrice;

	public GasStationFuelRate(String gasStationName) {
		this.gasStationName = gasStationName;
		fuelToPrice = new HashMap<>();
	}

	public void putRate(FuelName fuel, double price) {
		fuelToPrice.put(fuel, price);
	}
	public String getGasStationName() {
		return gasStationName;
	}
	public Map<FuelName, Double> getFuelToPrice() {
		return new HashMap<>(fuelToPrice);
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();

		result.append(System.lineSeparator()).append(getGasStationName()).append(System.lineSeparator());
		fuelToPrice.keySet().forEach(fuel -> result.append(fuel).append(" - ").
				append(fuelToPrice.get(fuel)).append(System.lineSeparator()));

		return result.toString();
	}
}
