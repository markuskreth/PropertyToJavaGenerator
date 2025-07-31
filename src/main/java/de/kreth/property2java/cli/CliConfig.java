package de.kreth.property2java.cli;

import java.io.IOException;


import de.kreth.property2java.cli.ArgumentConfiguration.Builder;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.MissingOptionException;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class CliConfig {

	private final Options options = options();

	private Options options() {
		Options retVal = new Options();
		retVal.addOption(Option.builder("t").longOpt("targetSourcePath").hasArg().required().build());
		retVal.addOption(Option.builder("f").longOpt("files").hasArgs().required().valueSeparator(',').build());
		retVal.addOption(Option.builder("p").longOpt("package").hasArg().required(false).build());
		return retVal;
	}

	public void fill(Builder builder, String[] args) throws IOException {

		CommandLineParser parser = new DefaultParser();
		try {
			CommandLine cmd = parser.parse(options, args);
			builder.setTarget(cmd.getOptionValue("t", "."));
			builder.setPackageName(cmd.getOptionValue("p"));
			for (String value : cmd.getOptionValues("f")) {
				builder.addPropFile(value);
			}
		} catch (MissingOptionException e) {
			printHelp();
			throw new IllegalStateException(e);
		} catch (ParseException e) {
			throw new IOException("Unable to parse Arguments", e);
		}
	}

	public void printHelp() {
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("Generator", options);
	}
}
