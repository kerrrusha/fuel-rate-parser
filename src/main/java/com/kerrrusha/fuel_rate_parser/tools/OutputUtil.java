package com.kerrrusha.fuel_rate_parser.tools;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class OutputUtil {
	private static final PrintStream ps = new PrintStream(System.out, true, StandardCharsets.UTF_8);

	public static void println(String msg) {
		ps.println(msg);
	}
	public static PrintStream getPrintStream() {
		return ps;
	}
}
