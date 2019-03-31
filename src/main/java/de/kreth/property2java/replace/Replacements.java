package de.kreth.property2java.replace;

import java.util.ArrayList;
import java.util.List;

public class Replacements {

	private final List<Replacement> replacementList = new ArrayList<>();

	public boolean add(Replacement e) {
		return replacementList.add(e);
	}

}
