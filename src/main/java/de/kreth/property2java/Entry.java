package de.kreth.property2java;

public class Entry {

	public final String constant;

	public final String key;

	public final String value;

	public Entry(String constant, String key, String value) {
		super();
		this.constant = constant;
		this.key = key;
		this.value = value;
	}

	public String getConstant() {
		return constant;
	}

	public String getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		return "Entry [constant=" + constant + ", key=" + key + ", value=" + value + "]";
	}

}