<#include "parts/package_part.tpl">import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Properties;

import javax.annotation.processing.Generated;
<#include  "parts/additionalImports.tpl">

/**
 * Property keys from ${fileName}
 */
<#include  "parts/enum_head_values.tpl">
	private ${classname} (String value) {
		this.value = value;
	}

	private static Properties properties = new Properties();
	static {
		try {
			properties.load(${classname}.class.getResourceAsStream("/${fileName}"));
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	private final String value;

<#include  "parts/key_value_part.tpl">

	/**
	 * The text for this key from {@link Properties}
	 * @return human readable text
	 */
    public String getText() {
		return properties.getProperty(value);
    }

<#include  "parts/withOptions.tpl">
}
