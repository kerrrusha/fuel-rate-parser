package com.kerrrusha.scrapper_fuel_rate.presentation.cli;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class OutputUtil {
	private static final PrintStream ps = new PrintStream(System.out, true, StandardCharsets.UTF_8);

	public static void println(String msg) {
		ps.println(msg);
	}
}
