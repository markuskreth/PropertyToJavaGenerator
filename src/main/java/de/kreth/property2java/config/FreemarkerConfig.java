package de.kreth.property2java.config;

import java.io.IOException;

import de.kreth.property2java.processor.Format;
import freemarker.template.Configuration;
import freemarker.template.Template;

public enum FreemarkerConfig {

	INSTANCE;

	private final Configuration cfg;

	public Template getTemplate(Format format) throws IOException {
		switch (format) {
		case WithInitializer:
			return cfg.getTemplate("enum_template_with_initializer.tpl");
		case WithInnerPropertyLoader:
			return cfg.getTemplate("enum_template_with_inner_properties.tpl");
		case WithInnerPropertyResourceBundle:
			return cfg.getTemplate("enum_template_with_inner_propertyresourcebundle.tpl");
		case WithUnaryOperatorParameter:
			return cfg.getTemplate("enum_template.tpl");
		default:
			throw new IllegalArgumentException("Format " + format + " is not supported.");
		}

	}

	private FreemarkerConfig() {
		cfg = new Configuration(Configuration.VERSION_2_3_28);
		cfg.setClassForTemplateLoading(this.getClass(), "/template/");
		cfg.setDefaultEncoding("UTF-8");
	}

}
