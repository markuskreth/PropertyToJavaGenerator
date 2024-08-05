<#if package??>package ${package};

</#if>import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Properties;

import javax.annotation.processing.Generated;

/**
 * Property keys from ${fileName}
 */
@Generated(date = "${generation_date}", value = "${generator_name}")
public enum ${classname} {

<#list entries as e>
	/**
	 * ${e.key} = "${e.value}"
	 */
	${e.constant} ("${e.key}")<#sep>,
</#sep>
</#list>;

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

	/**
	 * Represented Key in property File.
	 * @return key
	 */
	public String getValue() {
		return value;
	}

	/**
	 * The Text for this Key from PropertyResourceBundle
	 * @return human readable text
	 */
    public String getText() {
		return properties.getProperty(value);
    }

}
