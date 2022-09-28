package com.kerrrusha.scrapper_fuel_rate;


import com.kerrrusha.scrapper_fuel_rate.presentation.cli.commands.MainCommand;
import picocli.CommandLine;

public class Main {
	public static void main(String[] args) {
		final CommandLine cmd = new CommandLine(new MainCommand());
		int exitCode = cmd.execute(args);
		System.exit(exitCode);
	}
}
