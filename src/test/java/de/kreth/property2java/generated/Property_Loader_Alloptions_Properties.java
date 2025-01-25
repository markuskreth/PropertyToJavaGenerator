package de.kreth.property2java.generated;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Properties;

import javax.annotation.processing.Generated;

/**
 * Property keys from property_loader_alloptions.properties
 */

@Generated(date = "25.01.2025, 23:42:57", value = "de.kreth.property2java.Generator")
public enum Property_Loader_Alloptions_Properties {

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
	private Property_Loader_Alloptions_Properties (String value) {
		this.value = value;
	}

	private static Properties properties = new Properties();
	static {
		try {
			properties.load(Property_Loader_Alloptions_Properties.class.getResourceAsStream("/property_loader_alloptions.properties"));
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	private final String value;

	/**
	 * Represented Key in property File.
	 * @return key
	 */
	public String getKey() {
		return value;
	}
	/**
	 * The text for this key from {@link Properties}
	 * @return human readable text
	 */
    public String getText() {
		return properties.getProperty(value);
    }

	/**
	 * The text for this key from {@link Properties} with args as replacements of placeholders.
	 * <p>Placeholder must be defined as {0}, {1} etc.
	 * @return human readable text
	 */
	public String getText(Object...objects) {
		String property = properties.getProperty(value);
		return doReplacements(property, objects);
	}

	private String doReplacements(String property, Object...objects) {
		StringBuilder text = new StringBuilder();
		int index = property.indexOf('{');
		text.append(property.substring(0, index));
		
		while (index >= 0) {
			index++;
			int endIndex = withEndIndex(index, property);
			if (endIndex >0) {
				String theIndex = property.substring(index, endIndex);
				int withIndex = Integer.valueOf(theIndex);
				if (withIndex+1> objects.length) {
					throw new IllegalStateException("No Argument for Index {" + theIndex
							+ "}" + " at Position=" + (index - 1) + " in \"" + property + "\"");
				}
				text.append(objects[withIndex].toString());
				index = property.indexOf('{', endIndex);
				if (index <0) {
					text.append(property.substring(endIndex + 1));
				} else {
					text.append(property.substring(endIndex + 1, index));
				}
			} else {
				endIndex = index;
				
				index = property.indexOf('{', index);
				if (index <0) {
					text.append('{');
					text.append(property.substring(endIndex));
				}
			}
		}
		return text.toString();
	}
	
	/**
	 * extracts the end index, if (and only if) the closing } exists and 
	 * between the indicee an integer value exists. 
	 * @param index
	 * @param property
	 * @return -1 if invalid or not existing
	 */
	private int withEndIndex(int index, String property) {
		
		int result = -1;
		int endIndex = property.indexOf('}', index);
		if (endIndex >index) {
			String between = property.substring(index, endIndex);
			if (between.length() > 0) {
				result = endIndex;
				for(int i=0; i<between.length(); i++) {
					if (Character.isDigit(between.charAt(i)) == false) {
						return -1;
					}
				}
			}
		}
		return result;
	}}
