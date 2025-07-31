package de.kreth.property2java;

import java.text.MessageFormat;

public enum GeneratorOptions {
	/**
	 * 
	 */
	WithSubstitutors,
	/**
	 * Add a format Method, which uses and supports {@link MessageFormat}. 
	 */
	WithMessageFormatter("java.text.MessageFormat");
	
	private final String[] additionalImport;

	private GeneratorOptions(String... additionalImport) {
		this.additionalImport = additionalImport!= null ? additionalImport : new String[]{};
	}
	
	String[] getAdditionalImport() {
		return additionalImport;
	}
}
