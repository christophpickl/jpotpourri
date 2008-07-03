package net.sourceforge.jpotpourri.gui.log4jlog;

import java.util.Date;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.spi.LoggingEvent;

public class Log4jGuiAdapter extends AppenderSkeleton {

	private PropertiesTable tableProps = new PropertiesTable();

//	static {
//		De.bug("Configuring property configurator with log4j.properties");
//		PropertyConfigurator.configure(Log4jGuiAdapter.class.getResource("/log4j.properties"));
//	}
	
	public Log4jGuiAdapter() {
		// nothing to do
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
		final Log4jGuiHandler handler;
		
		if(pool.isLog4jGuiHandlerRegistered(appenderName) == true) {
			handler = pool.getLog4jGuiHandlerInternal(appenderName);
		} else {
			De.bug("Creating new gui handler...");
			final PropertiesHandler handlerProps = new PropertiesHandler(this.getName());
			
			final Log4jGuiHandlerDefinition handlerDefinition = 
				handlerProps.toDefinition(
						this.tableProps.toDefinition());
			handler = pool.createLog4jGuiHandler(handlerDefinition);
		}
		
		handler.processLoggingEvent(new Log4jEvent(event, new Date()));
	}
	
	
	@Override
	public void close() {
		// nothing to do
	}

	@Override
	public boolean requiresLayout() {
		return false;
	}
// TODO enable table configuration setting -> got: log4j:WARN Failed to set property [tableConfiguration] to value "net.sourceforge.jpotpourri.gui.widget.log4jlog.TableConfiguration". 
//	public TableConfiguration getTableConfiguration() {
//		De.bug("Adapter.getTableConfiguration(" + this.tableConfiguration + ")");
//		return this.tableConfiguration;
//	}
//
//	public void setTableConfiguration(final TableConfiguration tableConfiguration) {
//		De.bug("Adapter.setTableConfiguration(" + tableConfiguration + ")");
//		this.tableConfiguration = tableConfiguration;
//	}

	
	
	
	
	
	

	public String getTableColorEven() {
		return this.tableProps.getColorEven();
	}
	public void setTableColorEven(String colorEven) {
		this.tableProps.setColorEven(colorEven);
	}
	public String getTableColorOdd() {
		return this.tableProps.getColorOdd();
	}
	public void setTableColorOdd(String colorOdd) {
		this.tableProps.setColorOdd(colorOdd);
	}
	public String getTableRows() {
		return this.tableProps.getRows();
	}
	public void setTableRows(String rows) {
		this.tableProps.setRows(rows);
	}
	
	
	
	
}
