package net.sourceforge.jpotpourri.gui.log4jlog.gui.table;

import net.sourceforge.jpotpourri.gui.log4jlog.Log4jEvent;


abstract class LogTableColumn {

	public static final LogTableColumn LEVEL = new LogTableColumn("Level") {
		@Override
		public Object getTableModelValue(Log4jEvent event) {
			return event.getLevel().toString();
		}
	};
	public static final LogTableColumn MESSAGE = new LogTableColumn("Message") {
		@Override
		public Object getTableModelValue(Log4jEvent event) {
			return event.getMessage();
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
