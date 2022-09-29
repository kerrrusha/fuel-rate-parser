package com.kerrrusha.fuel_rate_parser;


import com.kerrrusha.fuel_rate_parser.presentation.cli.commands.MainCommand;
import picocli.CommandLine;

public class Main {
	public static void main(String[] args) {
		final CommandLine cmd = new CommandLine(new MainCommand());
		int exitCode = cmd.execute(args);
		System.exit(exitCode);
	}
}
