package net.sourceforge.jpotpourri.gui.log4jlog;


public class Log4jGuiHandlerDefinition {

	private final String appenderName;
	private final boolean systemLafEnabled;
	private final Log4jGuiTableDefinition tableDefinition;
	
	public Log4jGuiHandlerDefinition(final String appenderName,
			final boolean systemLafEnabled, final Log4jGuiTableDefinition tableDefinition) {
		De.bug("new Log4jGuiHandlerDefinition(appenderName="+appenderName+";systemLafEnabled="+systemLafEnabled+"); thread=" +
				Thread.currentThread().getName());
		if(appenderName == null) {
			throw new NullPointerException("appenderName");
		}
		if(tableDefinition == null) {
			throw new NullPointerException("tableDefinition");
		}
		
		this.appenderName = appenderName;
		this.systemLafEnabled = systemLafEnabled;
		
		this.tableDefinition = tableDefinition;

	}
	public String getAppenderName() {
		return this.appenderName;
	}
	public Log4jGuiTableDefinition getTableDefinition() {
		return this.tableDefinition;
	}
	public boolean isSystemLafEnabled() {
		return this.systemLafEnabled;
	}
}
