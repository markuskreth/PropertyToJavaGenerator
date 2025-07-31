package de.kreth.property2java.parts;

import static de.kreth.property2java.parts.ReplaceLogicForTemplate.doReplacements;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


import org.junit.jupiter.api.Test;

public class ReplaceLogicForTemplateTest {

	@Test
	void testPlaceholderOnly() {
		String property = "{0}";
		String replaced = doReplacements(property, "Replacement");
		assertThat(replaced).isEqualTo("Replacement");
	}

	@Test
	void testPlaceholderTrailing() {
		String property = "|{0}";
		String replaced = doReplacements(property, "Replacement");
		assertThat(replaced).isEqualTo("|Replacement");
	}

	@Test
	void testPlaceholderLeading() {
		String property = "{0}|";
		String replaced = doReplacements(property, "Replacement");
		assertThat(replaced).isEqualTo("Replacement|");
	}

	@Test
	void testPlaceholderOrdered() {
		String property = "Start{0}|{1}|{2}End";
		String replaced = doReplacements(property, "R1", "R2", "R3");
		assertThat(replaced).isEqualTo("StartR1|R2|R3End");
	}

	@Test
	void testPlaceholdersOrderedAtEdgesAndMissingIndex() {
		String property = "{0}||{2}";
		String replaced = doReplacements(property, "R1", "R2", "R3");
		assertThat(replaced).isEqualTo("R1||R3");
	}

	@Test
	void testSimpleReplacementWithEmpty() {
		String property = "Start{0}End";
		String replaced = doReplacements(property, "");
		assertThat(replaced).isEqualTo("StartEnd");
	}

	@Test
	void testSimpleReplacementToManyArguments() {
		String property = "Start{0}End";
		String replaced = doReplacements(property, "|0|", "notUsed");
		assertThat(replaced).isEqualTo("Start|0|End");
	}

	@Test
	void testNoReplacementWithoutIndex() {
		String property = "Start{}End";
		String replaced = doReplacements(property, "|0|", "notUsed");
		assertThat(replaced).isEqualTo("Start{}End");
	}

	@Test
	void testNoReplacementWithNoIntBetweenBraces() {
		String property = "Start{xy}End";
		String replaced = doReplacements(property, "|0|", "notUsed");
		assertThat(replaced).isEqualTo("Start{xy}End");
	}

	@Test
	void testMissingReplacement() {
		String property = "Start{1}End";
		assertThatThrownBy(() -> doReplacements(property, "|0|"))
			.isInstanceOf(IllegalStateException.class)
			.hasMessageContaining("{1}")		// Orignal Placeholder
			.hasMessageContaining("Position=5")	// Index of missing Placeholder
			.hasMessageContaining(property);	// Orignal Text
	}

}
