package de.kreth.property2java.generated;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestRealGenerated {

	@Test
	void propLoader() {
		assertEquals("Please set the price.", Property_Loader_Properties.MESSAGE_ARTICLE_PRICEERROR.getText());
	}

	@Test
	void resourceBundle() {
		assertEquals("Login Error! Wrong user or password?", Resource_Bundle_Properties.MESSAGE_USER_LOGINFAILURE.getText());
	}
	
}
