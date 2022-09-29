package com.kerrrusha.fuel_rate_parser.tools;

import com.kerrrusha.fuel_rate_parser.model.FuelName;
import com.kerrrusha.fuel_rate_parser.model.GasStationFuelRate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RatesToTablePreparingUtil {
	private static final String ABSENT_SYMBOL = " - ";
	private static final String GAS_STATION_NAME_COL_NAME = "GAS STATION";

	public static List<String> prepareHeader() {
		List<String> header = new ArrayList<>(List.of(GAS_STATION_NAME_COL_NAME));
		header.addAll(FuelName.toListString());
		return header;
	}
	public static String[][] prepareData(List<GasStationFuelRate> rates) {
		List<FuelName> fuelNameOrder = FuelName.toListEnum();
		List<String[]> data = new ArrayList<>();

		rates.forEach(rate -> {
			List<String> dataRow = new ArrayList<>();
			dataRow.add(rate.getGasStationName());
			Map<FuelName, Double> fuelToPrice = rate.getFuelToPrice();
			fuelNameOrder.forEach(fuelName -> {
				if (fuelToPrice.containsKey(fuelName)) {
					dataRow.add(NumberFormattingUtil.formatDouble(fuelToPrice.get(fuelName)));
				} else {
					dataRow.add(ABSENT_SYMBOL);
				}
			});
			data.add(dataRow.toArray(new String[0]));
		});

		return data.toArray(new String[0][0]);
	}
}
