package de.kreth.property2java.generated;

import java.util.Properties;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

import javax.annotation.processing.Generated;

/**
 * Property keys from unary_operator.properties
 * {@link #getValue()} gives the key for the entry, with {@link #getString(UnaryOperator<String>)}
 * the value is given directly.
 */
@Generated(date = "05.08.2024, 22:08:26", value = "de.kreth.property2java.Generator")
public enum Unary_Operator_Properties {

	/**
	 * label = ""
	 */
	LABEL ("label"),
	/**
	 * label.addarticle = "Add Article"
	 */
	LABEL_ADDARTICLE ("label.addarticle"),
	/**
	 * label.cancel = "Cancel"
	 */
	LABEL_CANCEL ("label.cancel"),
	/**
	 * label.close = "Close"
	 */
	LABEL_CLOSE ("label.close"),
	/**
	 * label.delete = "Delete"
	 */
	LABEL_DELETE ("label.delete"),
	/**
	 * label.discart = "Discart"
	 */
	LABEL_DISCART ("label.discart"),
	/**
	 * label.loggedin = "Logged in:"
	 */
	LABEL_LOGGEDIN ("label.loggedin"),
	/**
	 * label.logout = "Logout"
	 */
	LABEL_LOGOUT ("label.logout"),
	/**
	 * label.ok = "OK"
	 */
	LABEL_OK ("label.ok"),
	/**
	 * label.open = "Open"
	 */
	LABEL_OPEN ("label.open"),
	/**
	 * label.preview = "Preview"
	 */
	LABEL_PREVIEW ("label.preview"),
	/**
	 * label.store = "Store"
	 */
	LABEL_STORE ("label.store"),
	/**
	 * label.user.register = "Register"
	 */
	LABEL_USER_REGISTER ("label.user.register"),
	/**
	 * message.article.priceerror = "Please set the price."
	 */
	MESSAGE_ARTICLE_PRICEERROR ("message.article.priceerror"),
	/**
	 * message.delete.text = "Delete {0}?"
	 */
	MESSAGE_DELETE_TEXT ("message.delete.text"),
	/**
	 * message.delete.title = "Really delete?"
	 */
	MESSAGE_DELETE_TITLE ("message.delete.title"),
	/**
	 * message.invoiceitem.allfieldsmustbeset = "Start, end and article must not be 
                                         empty!"
	 */
	MESSAGE_INVOICEITEM_ALLFIELDSMUSTBESET ("message.invoiceitem.allfieldsmustbeset"),
	/**
	 * message.invoiceitem.startbeforeend = "End must be later than start."
	 */
	MESSAGE_INVOICEITEM_STARTBEFOREEND ("message.invoiceitem.startbeforeend"),
	/**
	 * message.user.create.success = "Thanks {0} created!"
	 */
	MESSAGE_USER_CREATE_SUCCESS ("message.user.create.success"),
	/**
	 * message.user.loginfailure = "Login Error! Wrong user or password?"
	 */
	MESSAGE_USER_LOGINFAILURE ("message.user.loginfailure"),
	/**
	 * message.user.passwordmissmatch = "Passwords don't match."
	 */
	MESSAGE_USER_PASSWORDMISSMATCH ("message.user.passwordmissmatch");

	private final String value;

	private Unary_Operator_Properties (String value) {
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
	 * Resolves the value for this key from the parameter function.
	 * <p>
	 * e.g. <code>Unary_Operator_Properties.getString(resBundle::getString)</code>
	 * @param resourceFunction {@link Properties#getProperty(String)} or {@link ResourceBundle#getString(String)}
	 * @return
	 */
	public String getString(UnaryOperator<String> resourceFunction) {
		return resourceFunction.apply(value);
	}
}
