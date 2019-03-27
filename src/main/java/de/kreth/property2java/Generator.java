package de.kreth.property2java;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import de.kreth.property2java.cli.ArgumentConfiguration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class Generator {

	private static final DateFormat dateTimeInstance = DateFormat.getDateTimeInstance();

	private final Configuration config;

	private final Template template;

	public Generator(Configuration config) {
		this.config = config;
		try {
			template = FreemarkerConfig.INSTANCE.getTemplate();
		}
		catch (IOException e) {
			throw new IllegalStateException("Unable to load freemarker template", e);
		}
	}

	public void start() throws IOException, GeneratorException {

		for (Map.Entry<String, Reader> entry : config.getInput().entrySet()) {
			String fileName = entry.getKey();
			try (Writer out = config.outWriter(fileName)) {

				Properties properties = new Properties();
				properties.load(entry.getValue());
				try {
					generate(properties, out, fileName, config);
				}
				catch (TemplateException e) {
					throw new GeneratorException("Error configuring Engine", e);
				}
			}
		}
	}

	void generate(Properties properties, Writer out, String fileName, Configuration config)
			throws IOException, TemplateException {

		Map<String, Object> root = new HashMap<>();
		root.put("generator_name", getClass().getName());
		root.put("generation_date", dateTimeInstance.format(new Date()));
		root.put("package", config.getPackage());
		root.put("fileName", fileName);
		root.put("classname", config.mapFilenameToClassName(fileName));

		List<Entry> entries = new ArrayList<>();

		root.put("entries", entries);

		@SuppressWarnings("unchecked")
		Enumeration<String> propertyNames = (Enumeration<String>) properties.propertyNames();

		while (propertyNames.hasMoreElements()) {
			final String propertyKeyString = propertyNames.nextElement();
			final String propertyValue = properties.getProperty(propertyKeyString);

			entries.add(new Entry(propertyKeyString.toUpperCase().replaceAll("[\\.-]", "_"), propertyKeyString,
					propertyValue));
		}
		template.process(root, out);
	}

	public static void main(String[] args) throws IOException, GeneratorException {
		Generator generator = new Generator(ArgumentConfiguration.parse(args));
		generator.start();
	}

	public class Entry {

		public final String constant;

		public final String key;

		public final String value;

		public Entry(String constant, String key, String value) {
			super();
			this.constant = constant;
			this.key = key;
			this.value = value;
		}

		public String getConstant() {
			return constant;
		}

		public String getKey() {
			return key;
		}

		public String getValue() {
			return value;
		}

		@Override
		public String toString() {
			return "Entry [constant=" + constant + ", key=" + key + ", value=" + value + "]";
		}

	}
}
