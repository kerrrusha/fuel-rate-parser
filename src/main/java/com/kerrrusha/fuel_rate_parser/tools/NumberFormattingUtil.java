package com.kerrrusha.fuel_rate_parser.tools;

import java.text.DecimalFormat;
import java.util.Locale;

public class NumberFormattingUtil {
	public static String formatDouble(double value) {
		return String.format(Locale.US, "%.2f", value);
	}
}
