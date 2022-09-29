package com.kerrrusha.fuel_rate_parser.model;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum FuelName {
	A95P, A95, A92, DT, GAS;

	public static List<FuelName> toListEnum() {
		return Stream.of(values()).collect(Collectors.toList());
	}
	public static List<String> toListString() {
		return toListEnum().stream().map(Enum::name).collect(Collectors.toList());
	}
}
