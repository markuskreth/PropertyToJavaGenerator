package de.kreth.property2java;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import de.kreth.property2java.cli.ArgumentConfiguration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class Generator {

	private Configuration config;

	private Template template;

	public Generator(Configuration config) {
		this.config = config;
		try {
			template = FreemarkerConfig.INSTANCE.getTemplate();
		}
		catch (IOException e) {

			throw new IllegalStateException("Unable to load freemarker template", e);
		}
	}

	public void start() throws IOException, TemplateException {

		for (Entry<String, Reader> entry : config.getInput().entrySet()) {
			String fileName = entry.getKey();
			Writer out = config.outWriter(fileName);
			Properties properties = new Properties();
			properties.load(entry.getValue());
			generate(properties, out, fileName, config);
		}
	}

	void generate(Properties properties, Writer out, String fileName, Configuration config)
			throws IOException, TemplateException {

		Map<String, Object> root = new HashMap<>();
		root.put("package", config.getPackage());
		root.put("fileName", fileName);
		root.put("classname", config.mapFilenameToClassName(fileName));

		List<Entry<String, String>> entries = new ArrayList<>();

		root.put("entries", entries);

		@SuppressWarnings("unchecked")
		Enumeration<String> propertyNames = (Enumeration<String>) properties.propertyNames();

		while (propertyNames.hasMoreElements()) {
			final String propertyKeyString = propertyNames.nextElement();
			final String propertyValue = properties.getProperty(propertyKeyString);

			Entry<String, String> entry = new Entry<String, String>() {

				@Override
				public String getKey() {
					return propertyKeyString.toUpperCase().replaceAll("[\\.-]", "_");
				}

				@Override
				public String getValue() {
					return propertyValue;
				}

				@Override
				public String setValue(String value) {
					throw new UnsupportedOperationException("Set Value not supported!");
				}
			};
			entries.add(entry);
		}
		template.process(root, out);
	}

	public static void main(String[] args) throws IOException, TemplateException {
		Generator generator = new Generator(ArgumentConfiguration.parse(args));
		generator.start();
	}

}
