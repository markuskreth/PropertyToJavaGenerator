<#if package??>package ${package};

</#if>import java.util.Properties;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

import javax.annotation.processing.Generated;

/**
 * Property keys from ${fileName}
 * {@link #getValue()} gives the key for the entry, with {@link #getText()} the value for the key is given directly.
 * This enum needs to be initialized before any use by {@link #init(UnaryOperator)}.
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

	private static UnaryOperator<String> function;
	private final String value;

	private ${classname} (String value) {
		this.value = value;
	}
	
	public static void init(UnaryOperator<String> resourceFunction) {
		function = resourceFunction;
	}
	
	/**
	 * Represented Key in property File.
	 * @return key
	 */
	public String getValue() {
		return value;
	}

	/**
	* Resolves the value for this key. 
	* {@link #init(UnaryOperator<String>)} must be called before.
	*/
	public String getText() {
		return function.apply(value);
	}
	
	/**
	 * Resolves the value for this key from the parameter function.
	 * <p>
	 * e.g. <code>${classname}.getString(resBundle::getString)</code>
	 * @param resourceFunction {@link Properties#getProperty(String)} or {@link ResourceBundle#getString(String)}
	 * @return
	 */
	public String getString(UnaryOperator<String> resourceFunction) {
		return resourceFunction.apply(value);
	}
}
