<#if package??>package ${package};

</#if>import java.util.Properties;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

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

	private final String value;

	private ${classname} (String value) {
		this.value = value;
	}

	/**
	 * Represented Key in property File.
	 * @return key
	 */
	public String getValue() {
		return value;
	}

    private static ResourceBundle bundle = PropertyResourceBundle.getBundle("${bundle_base_name}");

	/**
	 * The Text for this Key from PropertyResourceBundle
	 * @return human readable text
	 */
    public String getText() {
		return bundle.getString(value);
    }

}
