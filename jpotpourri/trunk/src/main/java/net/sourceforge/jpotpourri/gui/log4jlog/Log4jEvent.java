package net.sourceforge.jpotpourri.gui.log4jlog;

import java.util.Date;

import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;

public final class Log4jEvent {

	private final LoggingEvent loggingEvent;
	
	private final Date date;

	public Log4jEvent(final LoggingEvent loggingEvent, final Date date) {
		this.loggingEvent = loggingEvent;
		this.date = date;
	}

	
	public Date getDate() {
		return this.date;
	}
	
	public LoggingEvent getLoggingEvent() {
		return this.loggingEvent;
	}
	
	
	// DELEGATOR	
	public Level getLevel() {
		return this.loggingEvent.getLevel();
	}

	// DELEGATOR
	public Object getMessage() {
		return this.loggingEvent.getMessage();
	}
}
