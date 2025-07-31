package de.kreth.property2java.parts;

public final class ReplaceLogicForTemplate {

	static String doReplacements(String property, Object...objects) {
		StringBuilder text = new StringBuilder();
		int index = property.indexOf('{');
		text.append(property.substring(0, index));

		while (index >= 0) {
			index++;
			int endIndex = withEndIndex(index, property);
			if (endIndex >0) {
				String theIndex = property.substring(index, endIndex);
				int withIndex = Integer.valueOf(theIndex);
				if (withIndex+1> objects.length) {
					throw new IllegalStateException("No Argument for Index {" + theIndex +
							"}" + " at Position=" + (index - 1) + " in \"" + property + "\"");
				}
				text.append(objects[withIndex].toString());
				index = property.indexOf('{', endIndex);
				if (index <0) {
					text.append(property.substring(endIndex + 1));
				} else {
					text.append(property.substring(endIndex + 1, index));
				}
			} else {
				endIndex = index;

				index = property.indexOf('{', index);
				if (index <0) {
					text.append('{');
					text.append(property.substring(endIndex));
				}
			}
		}
		return text.toString();
	}

	/**
	 * extracts the end index, if (and only if) the closing } exists and 
	 * between the indicee an integer value exists. 
	 * @param index
	 * @param property
	 * @return -1 if invalid or not existing
	 */
	private static int withEndIndex(int index, String property) {

		int result = -1;
		int endIndex = property.indexOf('}', index);
		if (endIndex >index) {
			String between = property.substring(index, endIndex);
			if (between.length() > 0) {
				result = endIndex;
				for (int i = 0; i < between.length(); i++) {
					if (!Character.isDigit(between.charAt(i))) {
						return -1;
					}
				}
			}
		}
		return result;
	}

	private ReplaceLogicForTemplate() {
	}

}
