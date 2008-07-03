package net.sourceforge.jpotpourri.gui.log4jlog;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
class PropertiesHandler {
	
	private String appenderName;
	private boolean systemLafEnabled;
	// nothing else yet

	public PropertiesHandler() {
		// nothing to do
	}

	public Log4jGuiHandlerDefinition toDefinition(final Log4jGuiTableDefinition tableDefinition) {
		return new Log4jGuiHandlerDefinition(this.appenderName, systemLafEnabled, tableDefinition);
	}

	
	public void setAppenderName(String appenderName) {
		this.appenderName = appenderName;
	}
	
	public boolean isSystemLafEnabled() {
		return systemLafEnabled;
	}

	public void setSystemLafEnabled(boolean systemLafEnabled) {
		this.systemLafEnabled = systemLafEnabled;
	}
}
