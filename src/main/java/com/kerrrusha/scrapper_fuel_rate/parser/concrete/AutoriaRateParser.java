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

public class AutoriaRateParser implements ParseStrategy {
	private static final String GAS_STATION_NAME_CSS_SELECTOR = ".refuel a";
	private static final String ROWS_CSS_SELECTOR = ".refuelContent tr";
	private static final Map<FuelName, String> fuelNameToCssTags = Map.ofEntries(
			entry(FuelName.A95P, "a95p"),
			entry(FuelName.A95, "a95"),
			entry(FuelName.A92, "a92"),
			entry(FuelName.DT, "dt"),
			entry(FuelName.GAZ, "gaz")
	);
	private static final Map<GasStationCity, String> gasStationCityToEndpointName = Map.ofEntries(
			entry(GasStationCity.KYIV, "kiev"),
			entry(GasStationCity.LVIV, "lvov"),
			entry(GasStationCity.ODESA, "odessa"),
			entry(GasStationCity.CHERNIHIV, "chernigov"),
			entry(GasStationCity.TERNOPIL, "ternopol")
	);
	private static final String BASE_URL;

	static {
		Config config = Config.getInstance();
		BASE_URL = config.getValue(ConfigKey.AUTORIA_BASE_URL);
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

			ParserUtils.tryPutPrice(row, newRate, FuelName.A95P, "." + fuelNameToCssTags.get(FuelName.A95P) + " span");
			ParserUtils.tryPutPrice(row, newRate, FuelName.A95, "." + fuelNameToCssTags.get(FuelName.A95) + " span");
			ParserUtils.tryPutPrice(row, newRate, FuelName.A92, "." + fuelNameToCssTags.get(FuelName.A92) + " span");
			ParserUtils.tryPutPrice(row, newRate, FuelName.GAZ, "." + fuelNameToCssTags.get(FuelName.GAZ) + " span");
			ParserUtils.tryPutPrice(row, newRate, FuelName.DT, "." + fuelNameToCssTags.get(FuelName.DT) + " span");

			rates.add(newRate);
		});

		return rates;
	}

	private String prepareUrl(GasStationCity sourceCity) {
		return BASE_URL + gasStationCityToEndpointName.get(sourceCity) + "/";
	}
}
