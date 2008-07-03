package net.sourceforge.jpotpourri.gui.table;

import java.awt.Color;

import javax.swing.table.TableColumnModel;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
public interface ITableFillEmptyRowsReceiver {
	
	Color getColorRowBackgroundEven();

	Color getColorRowBackgroundOdd();
	
	int getRowCount();
	
	int getRowHeight();
	
	TableColumnModel getColumnModel();
	
	boolean getShowVerticalLines();
	
}
