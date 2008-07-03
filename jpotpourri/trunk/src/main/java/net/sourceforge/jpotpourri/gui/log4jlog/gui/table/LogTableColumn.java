package net.sourceforge.jpotpourri.gui.log4jlog.gui.table;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import net.sourceforge.jpotpourri.gui.log4jlog.Log4jEvent;


abstract class LogTableColumn {

	
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

	public static final LogTableColumn DATE = new LogTableColumn("Date") {
		@Override
		public Object getTableModelValue(Log4jEvent event) {
			return DATE_FORMAT.format(event.getDate());
		}
	};
	public static final LogTableColumn LEVEL = new LogTableColumn("Level") {
		@Override
		public Object getTableModelValue(Log4jEvent event) {
			return event.getLevel().toString();
		}
	};
	public static final LogTableColumn CLASS = new LogTableColumn("Class") {
		@Override
		public Object getTableModelValue(Log4jEvent event) {
			return event.getLogClassName();
		}
	};
	public static final LogTableColumn METHOD = new LogTableColumn("Method") {
		@Override
		public Object getTableModelValue(Log4jEvent event) {
			return event.getLogMethod();
		}
	};
	public static final LogTableColumn MESSAGE = new LogTableColumn("Message") {
		@Override
		public Object getTableModelValue(Log4jEvent event) {
			return event.getMessageRendered();
//			return event.getMessage();
		}
	};
	public static final LogTableColumn THREAD = new LogTableColumn("Thread") {
		@Override
		public Object getTableModelValue(Log4jEvent event) {
			return event.getThreadName();
		}
	};
	public static final LogTableColumn EXCEPTION = new LogTableColumn("Exception") {
		@Override
		public Object getTableModelValue(Log4jEvent event) {
			final Throwable t = event.getThrowable();
			if(t == null) {
				return "N/A";
			}
			return t.getClass().getName();
		}
	};
	
	private final String columnLabel;
	
	private LogTableColumn(String columnLabel) {
		this.columnLabel = columnLabel;
		
	}
	
	public String getColumnLabel() {
		return this.columnLabel;
	}
	
	public abstract Object getTableModelValue(final Log4jEvent event);
	
}
