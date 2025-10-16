package de.kreth.property2java;

import java.io.Serial;

public class GeneratorException extends Exception {

	@Serial
	private static final long serialVersionUID = -7319030228448260990L;

	public GeneratorException(String message, Throwable cause) {
		super(message, cause);
	}

}
