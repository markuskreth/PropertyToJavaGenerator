<#if package??>package ${package};

</#if>import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import javax.annotation.processing.Generated;

/**
 * Property keys from ${fileName}
 * {@link #getValue()} gives the key for the entry, with {@link #getText()} the value for the key is given directly.
 * Initializationis generated also.
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

    private static ResourceBundle bundle = PropertyResourceBundle.getBundle("${bundle_base_name}");

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

	/**
	 * The Text for this Key from PropertyResourceBundle
	 * @return human readable text
	 */
    public String getText() {
		return bundle.getString(value);
    }

}
