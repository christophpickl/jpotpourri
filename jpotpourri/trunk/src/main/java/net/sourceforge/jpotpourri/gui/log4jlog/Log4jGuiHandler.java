package net.sourceforge.jpotpourri.gui.log4jlog;

import javax.swing.JComponent;

import net.sourceforge.jpotpourri.gui.log4jlog.gui.Log4jLogPanel;

// actually most important class for developer/client
public class Log4jGuiHandler {

	private final Log4jLogPanel panel;

//	private final Log4jGuiHandlerDefinition handlerDefinition;

	Log4jGuiHandler(Log4jGuiHandlerDefinition handlerDefinition) {
		De.bug("new Log4jGuiHandler(handlerDefinition=" + handlerDefinition + ")");
//		this.handlerDefinition = handlerDefinition;
		
		this.panel = new Log4jLogPanel(handlerDefinition);
	}
	
	public void processLoggingEvent(final Log4jEvent event) {
		this.panel.processLoggingEvent(event);
	}
	
	public JComponent getJComponent() {
		return this.panel;
	}
}
