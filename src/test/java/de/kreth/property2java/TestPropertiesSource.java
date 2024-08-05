package de.kreth.property2java;

import java.io.StringReader;

public class TestPropertiesSource {

	public static StringReader testProperties() {
		return new StringReader("\r\n" + "label = \r\n" + "\r\n" + "label.addarticle    = Add Article\r\n"
				+ "label.cancel        = Cancel\r\n" + "label.close         = Close\r\n"
				+ "label.delete        = Delete\r\n" + "label.discart       = Discart\r\n"
				+ "label.loggedin      = Logged in:\r\n" + "label.logout        = Logout\r\n"
				+ "label.ok            = OK\r\n" + "label.store         = Store\r\n"
				+ "label.preview       = Preview\r\n" + "label.open          = Open\r\n"
				+ "label.user.register = Register\r\n" + "\r\n"
				+ "message.article.priceerror             = Please set the price.\r\n"
				+ "message.delete.text                    = Delete {0}?\r\n"
				+ "message.delete.title                   = Really delete?\r\n"
				+ "message.invoiceitem.allfieldsmustbeset = Start, end and article must not be \\r\\n"
				+ "                                         empty!\r\n"
				+ "message.invoiceitem.startbeforeend     = End must be later than start.\r\n"
				+ "message.user.create.success            = Thanks {0} created!\r\n"
				+ "message.user.loginfailure              = Login Error! Wrong user or password?\r\n"
				+ "message.user.passwordmissmatch         = Passwords don't match.\r\n" + "");
	}
	
}
