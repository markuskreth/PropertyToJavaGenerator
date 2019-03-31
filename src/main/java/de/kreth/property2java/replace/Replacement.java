package de.kreth.property2java.replace;

import java.util.Arrays;
import java.util.List;

public class Replacement {

	private static final List<Character> CHARS_NOT_ALLOWED_BEFORE_PROPERTY = Arrays.asList('.');

	private final String packageName;

	private final String simpleClassName;

	private final String oldPropertyName;

	private final String newPropertyName;

	private String qualifiedClassName;

	private String qualifiedOldProperty;

	public Replacement(String packageName, String simpleClassName, String oldPropertyName, String newPropertyName) {
		super();
		this.packageName = packageName;
		this.simpleClassName = simpleClassName;
		this.oldPropertyName = oldPropertyName;
		this.newPropertyName = newPropertyName;
		this.qualifiedClassName = packageName + "." + simpleClassName;
		this.qualifiedOldProperty = qualifiedClassName + "." + oldPropertyName;
	}

	public String getOldPropertyName() {
		return oldPropertyName;
	}

	public String getNewPropertyName() {
		return newPropertyName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((newPropertyName == null) ? 0 : newPropertyName.hashCode());
		result = prime * result + ((oldPropertyName == null) ? 0 : oldPropertyName.hashCode());
		result = prime * result + ((packageName == null) ? 0 : packageName.hashCode());
		result = prime * result + ((simpleClassName == null) ? 0 : simpleClassName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Replacement other = (Replacement) obj;
		if (newPropertyName == null) {
			if (other.newPropertyName != null) {
				return false;
			}
		}
		else if (!newPropertyName.equals(other.newPropertyName)) {
			return false;
		}
		if (oldPropertyName == null) {
			if (other.oldPropertyName != null) {
				return false;
			}
		}
		else if (!oldPropertyName.equals(other.oldPropertyName)) {
			return false;
		}
		if (packageName == null) {
			if (other.packageName != null) {
				return false;
			}
		}
		else if (!packageName.equals(other.packageName)) {
			return false;
		}
		if (simpleClassName == null) {
			if (other.simpleClassName != null) {
				return false;
			}
		}
		else if (!simpleClassName.equals(other.simpleClassName)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Replacement [packageName=" + packageName + ", simpleClassName=" + simpleClassName + ", oldPropertyName="
				+ oldPropertyName + ", newPropertyName=" + newPropertyName + "]";
	}

	public String replaceOccurrences(String sourceCode) {

		if (sourceCode.contains("import static " + qualifiedOldProperty)) {
			sourceCode = replaceStaticImportedUsages(sourceCode);
		}
		if (sourceCode.contains(qualifiedOldProperty)) {
			sourceCode = replaceFullQualifiedUnimported(sourceCode);
		}
		if (sourceCode.contains("import " + qualifiedClassName)) {
			sourceCode = replaceClassUsages(sourceCode);
		}
		return sourceCode;
	}

	private String replaceClassUsages(String sourceCode) {
		String search = this.simpleClassName + "." + oldPropertyName;
		String replacement = this.simpleClassName + "." + newPropertyName;

		int index = sourceCode.indexOf(search);
		while (index > 0) {
			if (!CHARS_NOT_ALLOWED_BEFORE_PROPERTY.contains(sourceCode.charAt(index - 1))) {
				StringBuilder source = new StringBuilder(sourceCode);
				source.replace(index, index + search.length(), replacement);
				sourceCode = source.toString();
			}
			index = sourceCode.indexOf(search, index + 1);
		}
		return sourceCode;
	}

	private String replaceStaticImportedUsages(String sourceCode) {
		int index = sourceCode.indexOf(oldPropertyName);
		while (index > 0) {
			if (!CHARS_NOT_ALLOWED_BEFORE_PROPERTY.contains(sourceCode.charAt(index - 1))) {
				StringBuilder source = new StringBuilder(sourceCode);
				source.replace(index, index + oldPropertyName.length(), newPropertyName);
				sourceCode = source.toString();
			}
			index = sourceCode.indexOf(oldPropertyName, index + 1);
		}
		return sourceCode;
	}

	private String replaceFullQualifiedUnimported(String sourceCode) {
		return sourceCode.replace(qualifiedOldProperty,
				qualifiedClassName + "." + newPropertyName);
	}

}
