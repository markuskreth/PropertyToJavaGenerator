package de.kreth.property2java.generated;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Properties;

import javax.annotation.processing.Generated;

/**
 * Property keys from property_loader.properties
 */
@Generated(date = "05.08.2024, 22:08:26", value = "de.kreth.property2java.Generator")
public enum Property_Loader_Properties {

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

	private Property_Loader_Properties (String value) {
		this.value = value;
	}

	private static Properties properties = new Properties();
	static {
		try {
			properties.load(Property_Loader_Properties.class.getResourceAsStream("/property_loader.properties"));
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
