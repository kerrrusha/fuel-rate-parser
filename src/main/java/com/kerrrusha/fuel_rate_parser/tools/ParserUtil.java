package com.kerrrusha.fuel_rate_parser.tools;

import com.kerrrusha.fuel_rate_parser.model.FuelName;
import com.kerrrusha.fuel_rate_parser.model.GasStationFuelRate;
import com.kerrrusha.fuel_rate_parser.parser.concrete.AutoriaRateParser;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Element;

public class ParserUtil {
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
