package de.kreth.property2java.config;

import java.io.IOException;

import freemarker.template.Configuration;
import freemarker.template.Template;

public enum FreemarkerConfigImpl implements FreemarkerConfig {

	INSTANCE;

	private final Configuration cfg;

	public Template getTemplate() throws IOException {
		return cfg.getTemplate("enum_template.tpl");
	}

	private FreemarkerConfigImpl() {
		cfg = new Configuration(Configuration.VERSION_2_3_28);
		cfg.setClassForTemplateLoading(this.getClass(), "/template/");
		cfg.setDefaultEncoding("UTF-8");
	}

}
