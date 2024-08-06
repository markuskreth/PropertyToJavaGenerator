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
	
	@Test
	void substitution() {
		String text = Property_Loader_Options_Properties.MESSAGE_WITH_FIVE_PLACEHOLDERS.getText("|R1|", "|R2|", "|R3|", "|R4|", "|R5|");
		assertEquals("Third is first|R3|, then last \"|R5|\", second=|R2|, fourth=|R4| and first is last=|R1|", text);
	}
}
