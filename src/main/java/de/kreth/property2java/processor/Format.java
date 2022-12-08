package de.kreth.property2java.processor;

import java.util.PropertyResourceBundle;

public enum Format {

    /**
     * Offers a getString(UnaryOperator<String> resourceFunction) method to access
     * the String value
     */
    WithUnaryOperatorParameter,
    /**
     * Generates {@link PropertyResourceBundle} to offer a getText() method without
     * parameters.
     */
    WithInnerPropertyResourceBundle,
    /**
     * Offers a generated {@link PropertyResourceBundle} to offer a getText() method
     * without parameters.
     */
    WithInnerPropertyLoader,
    /**
     * Offers a static init(UnaryOperator<String> resourceFunction) method to offer
     * a getText() method. The init method must be called before any getText() call.
     */
    WithInitializer
}
