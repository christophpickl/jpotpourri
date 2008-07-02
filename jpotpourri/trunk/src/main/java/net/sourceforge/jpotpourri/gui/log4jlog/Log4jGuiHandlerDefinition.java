package net.sourceforge.jpotpourri.gui.log4jlog;


public class Log4jGuiHandlerDefinition {

	private final String appenderName;
	
	private final Log4jGuiTableDefinition tableDefinition;
	
	public Log4jGuiHandlerDefinition(final String appenderName, final Log4jGuiTableDefinition tableDefinition) {
		if(appenderName == null) {
			throw new NullPointerException("appenderName");
		}
		if(tableDefinition == null) {
			throw new NullPointerException("tableDefinition");
		}
		
		this.appenderName = appenderName;
		this.tableDefinition = tableDefinition;

	}
	public String getAppenderName() {
		return this.appenderName;
	}
	public Log4jGuiTableDefinition getTableDefinition() {
		return this.tableDefinition;
	}
}
