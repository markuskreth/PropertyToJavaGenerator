package de.kreth.property2java;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import freemarker.template.Configuration;
import freemarker.template.Template;

public enum FreemarkerConfig {

	INSTANCE;

	private final Configuration cfg;

	public Template getTemplate() throws IOException {
		return cfg.getTemplate("enum_template.tpl");
	}

	private FreemarkerConfig() {
		cfg = new Configuration(Configuration.VERSION_2_3_28);
		URL url = getClass().getResource("/template/enum_template.tpl");
		try {
			cfg.setDirectoryForTemplateLoading(new File(url.getFile()).getParentFile());
		}
		catch (IOException e) {
			throw new IllegalStateException("Unable to configure freemarker", e);
		}

		cfg.setDefaultEncoding("UTF-8");
	}

}
