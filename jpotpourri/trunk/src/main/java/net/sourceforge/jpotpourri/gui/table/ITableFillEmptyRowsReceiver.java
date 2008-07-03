package net.sourceforge.jpotpourri.gui.table;

import java.awt.Color;

import javax.swing.table.TableColumnModel;

public interface ITableFillEmptyRowsReceiver {
	
	Color getColorRowBackgroundEven();

	Color getColorRowBackgroundOdd();
	
	int getRowCount();
	
	int getRowHeight();
	
	TableColumnModel getColumnModel();
	
	boolean getShowVerticalLines();
	
}
