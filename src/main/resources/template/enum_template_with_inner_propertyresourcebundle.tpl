<#include  "parts/package_part.tpl">import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import javax.annotation.processing.Generated;
<#include  "parts/additionalImports.tpl">

/**
 * Property keys from ${fileName}
 * {@link #getValue()} gives the key for the entry, with {@link #getText()} the value for the key is given directly.
 * Initializationis generated also.
 */
<#include  "parts/enum_head_values.tpl">
    private static ResourceBundle bundle = PropertyResourceBundle.getBundle("${bundle_base_name}");

	private final String value;

	private ${classname} (String value) {
		this.value = value;
	}

<#include  "parts/key_value_part.tpl">

	/**
	 * The Text for this Key from PropertyResourceBundle
	 * @return human readable text
	 */
    public String getText() {
		return bundle.getString(value);
    }

<#include  "parts/withOptions.tpl">
}
