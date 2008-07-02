package net.sourceforge.jpotpourri.gui.log4jlog.gui.table;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import net.sourceforge.jpotpourri.gui.ITableFillEmptyRowsReceiver;
import net.sourceforge.jpotpourri.gui.TableEmptyRowsPainter;
import net.sourceforge.jpotpourri.gui.log4jlog.De;
import net.sourceforge.jpotpourri.gui.log4jlog.Log4jGuiTableDefinition;

import org.jdesktop.swingx.JXTable;


public class Log4jTable extends JXTable implements ITableFillEmptyRowsReceiver {

	private static final long serialVersionUID = -5142437725186427922L;

	private final TableEmptyRowsPainter emptyRowsPainter;
	
	private final Color colorEven;
	private final Color colorOdd;
	
	
	
	public Log4jTable(final Log4jGuiTableDefinition tableDefinition) {
//		this.addHighlighter()
		De.bug("Creating new Log4jTable with defintion: " + tableDefinition);
		
		if(tableDefinition.getRows() != Log4jGuiTableDefinition.DEFAULT_ROWS) {
			this.setVisibleRowCount(tableDefinition.getRows());
		}
		
		this.colorEven = tableDefinition.getColorEven();
		this.colorOdd = tableDefinition.getColorOdd();
		
        this.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
        this.setColumnSelectionAllowed(false);
        this.setRowSelectionAllowed(true);
//        this.setColumnControlVisible(true);
//        this.packAll();
        
        this.emptyRowsPainter = new TableEmptyRowsPainter(this);
	}
	

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		this.emptyRowsPainter.delegatePaint(g);
	}

	public Color getColorRowBackgroundEven() {
		return this.colorEven;
	}

	public Color getColorRowBackgroundOdd() {
		return this.colorOdd;
	}
	



	@Override
	public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
		final Component c = super.prepareRenderer(renderer, row, column);
//		System.out.println("prepareRenderer: " + this.hashCode());
		
		
		
		final Color bgColor;
//        boolean focused = hasFocus();
        boolean selected = isCellSelected(row, column);
        if(selected) {
        	bgColor = Color.BLACK;
        } else {
        	final boolean isEven = row % 2 == 0;
        	bgColor = isEven ? this.colorEven : this.colorOdd;
        }
        c.setBackground(bgColor);
		
		return c;
	}




	
}
