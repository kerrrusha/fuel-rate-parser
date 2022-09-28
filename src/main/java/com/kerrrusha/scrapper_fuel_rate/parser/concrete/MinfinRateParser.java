package com.kerrrusha.scrapper_fuel_rate.parser.concrete;

import com.kerrrusha.scrapper_fuel_rate.config.Config;
import com.kerrrusha.scrapper_fuel_rate.config.ConfigKey;
import com.kerrrusha.scrapper_fuel_rate.model.FuelName;
import com.kerrrusha.scrapper_fuel_rate.model.GasStationFuelRate;
import com.kerrrusha.scrapper_fuel_rate.parser.GasStationCity;
import com.kerrrusha.scrapper_fuel_rate.parser.ParseStrategy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.Map.entry;

public class MinfinRateParser implements ParseStrategy {
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
			String gasStationName = ParserUtils.getTextOfFirstInRow(row, GAS_STATION_NAME_CSS_SELECTOR);

			GasStationFuelRate newRate = new GasStationFuelRate(gasStationName);

			ParserUtils.tryPutPrice(row, newRate, FuelName.A95P, "td:nth-child(3)");
			ParserUtils.tryPutPrice(row, newRate, FuelName.A95, "td:nth-child(4)");
			ParserUtils.tryPutPrice(row, newRate, FuelName.A92, "td:nth-child(5)");
			ParserUtils.tryPutPrice(row, newRate, FuelName.GAZ, "td:nth-child(7)");
			ParserUtils.tryPutPrice(row, newRate, FuelName.DT, "td:nth-child(6)");

			rates.add(newRate);
		});

		return rates;
	}
	private String prepareUrl(GasStationCity sourceCity) {
		return BASE_URL + gasStationCityToEndpointName.get(sourceCity) + "/";
	}
}
