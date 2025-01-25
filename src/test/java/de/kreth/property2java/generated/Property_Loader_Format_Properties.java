package de.kreth.property2java.generated;

import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import javax.annotation.processing.Generated;
import java.text.MessageFormat;
;

/**
 * Property keys from property_loader_format.properties
 * {@link #getValue()} gives the key for the entry, with {@link #getText()} the value for the key is given directly.
 * Initializationis generated also.
 */

@Generated(date = "25.01.2025, 23:42:57", value = "de.kreth.property2java.Generator")
public enum Property_Loader_Format_Properties {

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
	MESSAGE_USER_PASSWORDMISSMATCH ("message.user.passwordmissmatch"),
	/**
	 * message.with.five.placeholders = "Third is first{2}, then last "{4}", second={1}, fourth={3} and first is last={0}"
	 */
	MESSAGE_WITH_FIVE_PLACEHOLDERS ("message.with.five.placeholders");
    private static ResourceBundle bundle = PropertyResourceBundle.getBundle("property_loader_format");

	private final String value;

	private Property_Loader_Format_Properties (String value) {
		this.value = value;
	}

	/**
	 * Represented Key in property File.
	 * @return key
	 */
	public String getKey() {
		return value;
	}
	/**
	 * The Text for this Key from PropertyResourceBundle
	 * @return human readable text
	 */
    public String getText() {
		return bundle.getString(value);
    }


    private MessageFormat messageFormat = null;
    public String format(Object...objects) {
    	if (messageFormat == null) {
    		messageFormat = new MessageFormat(getText());
    	}
    	return messageFormat.format(objects, new StringBuffer(), null).toString();
    }}
