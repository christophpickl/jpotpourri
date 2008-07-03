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
		return new Log4jGuiHandlerDefinition(this.appenderName, this.systemLafEnabled, tableDefinition);
	}

	
	public void setAppenderName(final String appenderName) {
		this.appenderName = appenderName;
	}
	
	public boolean isSystemLafEnabled() {
		return this.systemLafEnabled;
	}

	public void setSystemLafEnabled(final boolean systemLafEnabled) {
		this.systemLafEnabled = systemLafEnabled;
	}
}
