package de.kreth.property2java;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;

import de.kreth.property2java.generated.GenerateTheTest;

public class TestPropertiesSource {

	public static StringReader testProperties() {
		return new StringReader("""
				
				label = 
				
				label.addarticle    = Add Article
				label.cancel        = Cancel
				label.close         = Close
				label.delete        = Delete
				label.discart       = Discart
				label.loggedin      = Logged in:
				label.logout        = Logout
				label.ok            = OK
				label.store         = Store
				label.preview       = Preview
				label.open          = Open
				label.user.register = Register
				
				message.article.priceerror             = Please set the price.
				message.delete.text                    = Delete {0}?
				message.delete.title                   = Really delete?
				message.invoiceitem.allfieldsmustbeset = Start, end and article must not be \\r\\n\
				                                         empty!
				message.invoiceitem.startbeforeend     = End must be later than start.
				message.user.create.success            = Thanks {0} created!
				message.user.loginfailure              = Login Error! Wrong user or password?
				message.user.passwordmissmatch         = Passwords don't match.
				message.with.five.placeholders         = Third is first{2}, then last "{4}", second={1}, fourth={3} and first is last={0}
				""");
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
