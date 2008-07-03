package net.sourceforge.jpotpourri.gui.log4jlog.gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import net.sourceforge.jpotpourri.gui.log4jlog.De;
import net.sourceforge.jpotpourri.gui.log4jlog.Log4jEvent;
import net.sourceforge.jpotpourri.gui.log4jlog.TableFilter;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
final class Log4jTableModel extends AbstractTableModel {

	private static final long serialVersionUID = -4864887345823485298L;

	private final ModelCoreData data = new ModelCoreData();

	
	private static final List<LogTableColumn> TABLE_COLUMNS = new ArrayList<LogTableColumn>(2);
	static {
		TABLE_COLUMNS.add(LogTableColumn.DATE);
		TABLE_COLUMNS.add(LogTableColumn.LEVEL);
		TABLE_COLUMNS.add(LogTableColumn.CLASS);
		TABLE_COLUMNS.add(LogTableColumn.METHOD);
		
		TABLE_COLUMNS.add(LogTableColumn.MESSAGE);
		TABLE_COLUMNS.add(LogTableColumn.THREAD);
		TABLE_COLUMNS.add(LogTableColumn.EXCEPTION);
		
	}

	
	
	public Log4jTableModel() {
		// nothing to do
	}

	// ----------------- custom

	public void addLoggingEvent(final Log4jEvent event) {
		De.bug("Log4jTableModel --- addLoggignEvent(" + event + ")");
		this.data.addLoggingEvent(event);
		this.fireTableDataChanged();
	}
	
	public void doFilter(final TableFilter filter) {
		De.bug("Log4jTableModel --- doFilter(" + filter + ")");
		this.data.doFilter(filter);
		this.fireTableDataChanged();
	}
	
	public void doClearData() {
		De.bug("Log4jTableModel --- doClearData()");
		this.data.doClear();
		this.fireTableDataChanged();
	}
	
	// ----------------- table model optional
	
	@Override
	@SuppressWarnings("unused")
	public boolean isCellEditable(final int rowIndex, final int columnIndex) {
		return false;
	}

	
	@Override
	public String getColumnName(final int columnIndex) {
		return TABLE_COLUMNS.get(columnIndex).getColumnLabel();
	}
	
	// ----------------- table model mandatory
	
	public int getColumnCount() {
		return TABLE_COLUMNS.size();
	}

	public int getRowCount() {
		return this.data.getSize();
	}

	public Object getValueAt(final int rowIndex, final int columnIndex) {
		final Log4jEvent event = this.data.get(rowIndex);
		return TABLE_COLUMNS.get(columnIndex).getTableModelValue(event);
		
	}

}
