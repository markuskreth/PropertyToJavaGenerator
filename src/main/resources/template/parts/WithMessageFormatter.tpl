
    private MessageFormat messageFormat = null;
    public String format(Object...objects) {
    	if (messageFormat == null) {
    		messageFormat = new MessageFormat(getText());
    	}
    	return messageFormat.format(objects, new StringBuffer(), null).toString();
    }