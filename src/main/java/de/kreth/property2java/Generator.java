package de.kreth.property2java;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Enumeration;
import java.util.Map.Entry;
import java.util.Properties;

import de.kreth.property2java.cli.ArgumentConfiguration;

public class Generator {

	private Configuration config;

	public Generator(Configuration config) {
		this.config = config;
	}

	public void start() throws IOException {

		for (Entry<String, Reader> entry : config.getInput().entrySet()) {
			String fileName = entry.getKey();
			Writer out = config.outWriter(fileName);
			Properties properties = new Properties();
			properties.load(entry.getValue());
			generate(properties, out, fileName, config);
		}
	}

	void generate(Properties properties, Writer out, String fileName, Configuration config) throws IOException {

		@SuppressWarnings("unchecked")
		Enumeration<String> propertyNames = (Enumeration<String>) properties.propertyNames();
		String packageName = config.getPackage();
		if (packageName != null && !packageName.isBlank()) {
			out.write("package ");
			out.write(packageName);
			out.write(";\n\n");
		}
		out.write("public interface ");
		out.write(config.mapFilenameToClassName(fileName));
		out.write(" {\n");
		while (propertyNames.hasMoreElements()) {
			String key = propertyNames.nextElement();
			out.write(
					"\tpublic static String " + key.toUpperCase().replaceAll("[\\.-]", "_") + " = \"" + key + "\";\n");
		}
		out.write(" }\n");
		out.flush();
		out.close();
	}

	public static void main(String[] args) throws IOException {
		Generator generator = new Generator(ArgumentConfiguration.parse(args));
		generator.start();
	}

}
