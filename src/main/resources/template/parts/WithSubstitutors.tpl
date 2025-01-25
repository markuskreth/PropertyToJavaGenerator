	/**
	 * The text for this key from {@link Properties} with args as replacements of placeholders.
	 * <p>Placeholder must be defined as {0}, {1} etc.
	 * @return human readable text
	 */
	public String getText(Object...objects) {
		String property = getText();
		return doReplacements(property, objects);
	}

<#-- From here tested substitution method -->
	private String doReplacements(String property, Object...objects) {
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
					throw new IllegalStateException("No Argument for Index {" + theIndex
							+ "}" + " at Position=" + (index - 1) + " in \"" + property + "\"");
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
	private int withEndIndex(int index, String property) {
		
		int result = -1;
		int endIndex = property.indexOf('}', index);
		if (endIndex >index) {
			String between = property.substring(index, endIndex);
			if (between.length() > 0) {
				result = endIndex;
				for(int i=0; i<between.length(); i++) {
					if (Character.isDigit(between.charAt(i)) == false) {
						return -1;
					}
				}
			}
		}
		return result;
	}