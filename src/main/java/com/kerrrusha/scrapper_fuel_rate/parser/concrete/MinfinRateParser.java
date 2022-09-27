package com.kerrrusha.scrapper_fuel_rate.parser.concrete;

import com.kerrrusha.scrapper_fuel_rate.config.Config;
import com.kerrrusha.scrapper_fuel_rate.config.ConfigKey;
import com.kerrrusha.scrapper_fuel_rate.model.FuelName;
import com.kerrrusha.scrapper_fuel_rate.model.GasStationFuelRate;
import com.kerrrusha.scrapper_fuel_rate.parser.GasStationCity;
import com.kerrrusha.scrapper_fuel_rate.parser.ParseStrategy;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.Map.entry;

public class MinfinRateParser implements ParseStrategy {
	final static Logger logger = Logger.getLogger(MinfinRateParser.class);
	private static final String GAS_STATION_NAME_CSS_SELECTOR = "td:nth-child(1) > a";
	private static final String ROWS_CSS_SELECTOR = "#tm-table > table > tbody > tr:nth-child(+n+2)";
	private static final Map<GasStationCity, String> gasStationCityToEndpointName = Map.ofEntries(
			entry(GasStationCity.KYIV, "kievskaya"),
			entry(GasStationCity.LVIV, "lvovskaya"),
			entry(GasStationCity.ODESA, "odesskaya"),
			entry(GasStationCity.CHERNIHIV, "chernigovskaya"),
			entry(GasStationCity.TERNOPIL, "ternopolskaya")
	);
	private static final String BASE_URL;

	static {
		Config config = Config.getInstance();
		BASE_URL = config.getValue(ConfigKey.MINFIN_BASE_URL);
	}

	@Override
	public List<GasStationFuelRate> parse(GasStationCity sourceCity) throws IOException {
		List<GasStationFuelRate> rates = new ArrayList<>();

		final String URL = prepareUrl(sourceCity);
		final Document document = Jsoup.connect(URL).get();
		List<Element> rows = document.select(ROWS_CSS_SELECTOR);
		rows.forEach(row -> {
			String gasStationName = getTextOfFirstInRow(row, GAS_STATION_NAME_CSS_SELECTOR);

			GasStationFuelRate newRate = new GasStationFuelRate(gasStationName);

			tryPutPrice(row, newRate, FuelName.A95P, "td:nth-child(3)");
			tryPutPrice(row, newRate, FuelName.A95, "td:nth-child(4)");
			tryPutPrice(row, newRate, FuelName.A92, "td:nth-child(5)");
			tryPutPrice(row, newRate, FuelName.GAZ, "td:nth-child(7)");
			tryPutPrice(row, newRate, FuelName.DT, "td:nth-child(6)");

			rates.add(newRate);
		});

		return rates;
	}
	private void tryPutPrice(Element row, GasStationFuelRate rate, FuelName fuel, String cssSelector) {
		String priceStr = getTextOfFirstInRow(row, cssSelector);
		priceStr = priceStr.replace(',', '.');
		try {
			rate.putRate(fuel, Double.parseDouble(priceStr));
		} catch (NumberFormatException e) {
			logger.warn(fuel + " price of " + rate.getGasStationName() + " gas station is absent or broken.");
		}
	}
	private String getTextOfFirstInRow(Element row, String cssSelector) {
		Element element = row.selectFirst(cssSelector);
		assert element != null;
		return element.text();
	}
	private String prepareUrl(GasStationCity sourceCity) {
		return BASE_URL + gasStationCityToEndpointName.get(sourceCity) + "/";
	}
}
