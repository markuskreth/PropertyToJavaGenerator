package de.kreth.property2java;

import java.util.Properties;
import java.util.PropertyResourceBundle;

public enum Format {

	/**
	 * Offers a getString(UnaryOperator resourceFunction) method to access the
	 * String value
	 * <p>An accessor to the resource is needed, for example {@link Properties#getProperty(String)}
	 */
	WithUnaryOperatorParameter,
	/**
	 * Offers a static init(UnaryOperator resourceFunction) method to offer a
	 * getText() method. The init method must be called before any getText() call.
	 */
	WithInitializer,
	/**
	 * Generates {@link PropertyResourceBundle} to offer a getText() method without
	 * parameters. It is initialized automatically
	 * <p>The generated class supports localized property files by using {@link java.util.ResourceBundle}
	 * @see {@link java.util.ResourceBundle} for supported format and file names.
	 */
	WithInnerPropertyResourceBundle,
	/**
	 * Generates {@link Properties} to offer a getText() method
	 * without parameters. It is initialized automatically
	 * <p>The property file is loaded as {@link Class#getResourceAsStream(String)}
	 */
	WithInnerPropertyLoader,
}
