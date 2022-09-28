package com.kerrrusha.scrapper_fuel_rate.parser.concrete;

import com.kerrrusha.scrapper_fuel_rate.model.FuelName;
import com.kerrrusha.scrapper_fuel_rate.model.GasStationFuelRate;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Element;

public class ParserUtils {
	private final static Logger logger = Logger.getLogger(AutoriaRateParser.class);

	public static void tryPutPrice(Element row, GasStationFuelRate rate, FuelName fuel, String cssSelector) {
		String priceStr = getTextOfFirstInRow(row, cssSelector);
		try {
			rate.putRate(fuel, Double.parseDouble(priceStr));
		} catch (NumberFormatException e) {
			logger.warn(fuel + " price of " + rate.getGasStationName() + " gas station is absent or broken.");
		}
	}
	public static String getTextOfFirstInRow(Element row, String cssSelector) {
		Element element = row.selectFirst(cssSelector);
		assert element != null;
		return element.text();
	}
}
