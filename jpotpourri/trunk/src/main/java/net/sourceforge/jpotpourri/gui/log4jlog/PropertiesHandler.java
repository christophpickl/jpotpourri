package net.sourceforge.jpotpourri.gui.log4jlog;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
class PropertiesHandler {
	
	private final String appenderName;
	// nothing else yet
	
	public PropertiesHandler(String appenderName) {
		this.appenderName = appenderName;
	}
	
	public Log4jGuiHandlerDefinition toDefinition(final Log4jGuiTableDefinition tableDefinition) {
		return new Log4jGuiHandlerDefinition(this.appenderName, tableDefinition);
	}

}
