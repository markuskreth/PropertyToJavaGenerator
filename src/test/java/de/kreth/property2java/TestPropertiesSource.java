package de.kreth.property2java;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;

import de.kreth.property2java.generated.GenerateTheTest;

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
				+ "message.user.passwordmissmatch         = Passwords don't match.\r\n"
				+ "message.with.five.placeholders         = Third is first{2}, then last \"{4}\", second={1}, fourth={3} and first is last={0}\r\n");
	}
	
	public static void main(String[] args) throws IOException {
		File dir = new File("D:\\Markus\\programmierung\\workspace_clubhelper\\PropertyToJavaGenerator\\src\\test\\resources");

		try (FileWriter out = new FileWriter(new File(dir, GenerateTheTest.PROPERTY_LOADER_PROPERTIES))) {
			testProperties().transferTo(out);
			System.out.println("Generated: " + GenerateTheTest.PROPERTY_LOADER_PROPERTIES);
		}
		
		try (FileWriter out = new FileWriter(new File(dir, GenerateTheTest.UNARY_OPERATOR_PROPERTIES))) {
			testProperties().transferTo(out);
			System.out.println("Generated: " + GenerateTheTest.UNARY_OPERATOR_PROPERTIES);
		}
		try (FileWriter out = new FileWriter(new File(dir, GenerateTheTest.RESOURCE_BUNDLE))) {
			testProperties().transferTo(out);
			System.out.println("Generated: " + GenerateTheTest.RESOURCE_BUNDLE);
		}
		try (FileWriter out = new FileWriter(new File(dir, GenerateTheTest.PROPERTY_LOADER_OPTIONS_PROPERTIES))) {
			testProperties().transferTo(out);
			System.out.println("Generated: " + GenerateTheTest.PROPERTY_LOADER_OPTIONS_PROPERTIES);
		}
		
	}
}
