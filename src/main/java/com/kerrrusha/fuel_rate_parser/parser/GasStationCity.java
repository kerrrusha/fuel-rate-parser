package com.kerrrusha.fuel_rate_parser.parser;

public enum GasStationCity {
	KYIV, LVIV, ODESA, CHERNIHIV, TERNOPIL;

	public static String valuesString() {
		StringBuilder result = new StringBuilder();

		GasStationCity[] cities = values();
		for(int i = 0; i < cities.length; i++) {
			result.append(cities[i]);
			if (i != cities.length - 1) {
				result.append(", ");
			}
		}

		return result.toString();
	}
}
