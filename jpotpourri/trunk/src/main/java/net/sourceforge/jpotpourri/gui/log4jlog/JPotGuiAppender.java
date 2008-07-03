package net.sourceforge.jpotpourri.gui.log4jlog;

import java.util.Date;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.spi.LoggingEvent;

public class JPotGuiAppender extends AppenderSkeleton {

	private final PropertiesTable tableProps = new PropertiesTable();

	private final PropertiesHandler handlerProps = new PropertiesHandler();
	
//	static {
//		De.bug("Configuring property configurator with log4j.properties");
//		PropertyConfigurator.configure(Log4jGuiAdapter.class.getResource("/log4j.properties"));
//	}
	
	public JPotGuiAppender() {
		// nothing to do
		De.bug("new JPotGuiAppender()");
	}
	
	@Override
	protected void append(final LoggingEvent event) {
		final String fqcn = event.getLocationInformation().getClassName();
		if(Log4jGuiHandlerPool.class.getName().equals(fqcn)) {
			De.bug("Ignore appending log message from GuiHandlerPool.");
			return;
		}
		final Log4jGuiHandlerPool pool = Log4jGuiHandlerPool.getInstance();
		final String appenderName = this.getName();
		
		assert(pool.isLog4jGuiHandlerRegistered(appenderName) == true);
		final Log4jGuiHandler handler = pool.getLog4jGuiHandlerInternal(appenderName);
		
		handler.processLoggingEvent(new Log4jEvent(event, new Date()));
	}
	
	@Override
	public void activateOptions() {
		De.bug("JPotGuiAppender --- activating options");
		final Log4jGuiHandlerPool pool = Log4jGuiHandlerPool.getInstance();
		final String appenderName = this.getName();
		
		if(pool.isLog4jGuiHandlerRegistered(appenderName) == false) {
			De.bug("JPotGuiAppender --- Creating new gui handler for appender '" + appenderName + "'...");
			this.handlerProps.setAppenderName(appenderName);
			
			final Log4jGuiHandlerDefinition handlerDefinition = 
				this.handlerProps.toDefinition(
						this.tableProps.toDefinition());
			pool.createLog4jGuiHandler(handlerDefinition);
		}
		
	}
	
	@Override
	public void close() {
		// nothing to do
	}

	@Override
	public boolean requiresLayout() {
		return false;
	}
	
// does (maybe) work with log4j 1.3 enable table configuration setting -> got: log4j:WARN Failed to set property [tableConfiguration] to value "net.sourceforge.jpotpourri.gui.widget.log4jlog.TableConfiguration".
// if using only xml-configuration, one could use UnrecognizedXmlStuff interface for this purpose (works fine; but not for *.properties files!)
//	public TableConfiguration getTableConfiguration() {
//		De.bug("Adapter.getTableConfiguration(" + this.tableConfiguration + ")");
//		return this.tableConfiguration;
//	}
//
//	public void setTableConfiguration(final TableConfiguration tableConfiguration) {
//		De.bug("Adapter.setTableConfiguration(" + tableConfiguration + ")");
//		this.tableConfiguration = tableConfiguration;
//	}

	

	public boolean isSystemLafEnabled() {
		return this.handlerProps.isSystemLafEnabled();
	}

	public void setSystemLafEnabled(boolean systemLafEnabled) {
		this.handlerProps.setSystemLafEnabled(systemLafEnabled);
	}
	
	
	
	

	public String getColorRowBackgroundEven() {
		return this.tableProps.getColorRowBackgroundEven();
	}
	public void setColorRowBackgroundEven(String colorEven) {
		this.tableProps.setColorRowBackgroundEven(colorEven);
	}
	public String getColorRowBackgroundOdd() {
		return this.tableProps.getColorRowBackgroundOdd();
	}
	public void setColorRowBackgroundOdd(String colorOdd) {
		this.tableProps.setColorRowBackgroundOdd(colorOdd);
	}
	public String getTableRows() {
		return this.tableProps.getTableRows();
	}
	public void setTableRows(String rows) {
		this.tableProps.setTableRows(rows);
	}


	public void setColorRowForeground(String fontColor) {
		this.tableProps.setColorRowForeground(fontColor);
	}
	public void setColorSelectedRowForeground(String selectedFontColor) {
		this.tableProps.setColorSelectedRowForeground(selectedFontColor);
	}

	public void setColorSelectedRowBackground(String selectedBackgroundColor) {
		this.tableProps.setColorSelectedRowBackground(selectedBackgroundColor);
	}
	

//	public String getColorSelectedNofocusRowBackground() {
//		return this.tableProps.getColorSelectedNofocusRowBackground();
//	}
//
//	public void setColorSelectedNofocusRowBackground(String colorSelectedNofocusRowBackground) {
//		this.tableProps.setColorSelectedNofocusRowBackground(colorSelectedNofocusRowBackground);
//	}
	
}
