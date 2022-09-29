package com.kerrrusha.fuel_rate_parser.config;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

public final class Config {
	private static Config instance;

	private final Map<ConfigKey, String> data;

	private Config() {
		Optional<JSONObject> jsonOptional = readJsonObject();
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
			instance = new Config();
		}
		return instance;
	}

	public String getValue(ConfigKey key) {
		return data.get(key);
	}

	private Optional<JSONObject> readJsonObject() {
		try(InputStreamReader reader = new InputStreamReader(
				Objects.requireNonNull(
						Config.class.getResourceAsStream("/config.json")
				), StandardCharsets.UTF_8
		)) {
			return Optional.of((JSONObject) new JSONParser().parse(reader));
		} catch (IOException | ParseException e) {
			throw new RuntimeException(e);
		}
	}
}
