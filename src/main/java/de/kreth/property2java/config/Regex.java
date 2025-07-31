package de.kreth.property2java.config;

import java.util.regex.Pattern;

public final class Regex {

	public static final Pattern PATTERN = Pattern.compile("_[a-z]{2}(_[A-Z]{2})?\\.");

	private Regex() {
	}
}
