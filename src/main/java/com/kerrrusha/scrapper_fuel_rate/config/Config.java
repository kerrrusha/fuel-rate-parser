package com.kerrrusha.scrapper_fuel_rate.config;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public final class Config {
	private static final String jsonPath = Objects.requireNonNull(Config.class.
			getClassLoader().getResource("config.json")).getPath();
	private static Config instance;

	private final Map<ConfigKey, String> data;

	private Config(String jsonPath) {
		Optional<JSONObject> jsonOptional = readJsonObject(jsonPath);
		data = new HashMap<>();
		if (jsonOptional.isEmpty()) {
			return;
		}
		putJsonValues(jsonOptional.get());
	}
	private void putJsonValues(JSONObject json) {
		List.of(ConfigKey.values()).forEach(key -> data.put(key, String.valueOf(json.get(key.name()))));
	}

	public static Config getInstance() {
		if (instance == null) {
			instance = new Config(jsonPath);
		}
		return instance;
	}

	public String getValue(ConfigKey key) {
		return data.get(key);
	}

	private Optional<JSONObject> readJsonObject(String jsonPath) {
		try(FileReader fr = new FileReader(jsonPath)) {
			return Optional.of((JSONObject) new JSONParser().parse(fr));
		} catch (IOException | ParseException e) {
			throw new RuntimeException(e);
		}
	}
}
