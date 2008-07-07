package net.sourceforge.jpotpourri.gui.log4jlog.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import net.sourceforge.jpotpourri.gui.log4jlog.De;
import net.sourceforge.jpotpourri.gui.log4jlog.Log4jGuiTableDefinition;
import net.sourceforge.jpotpourri.gui.table.ITableBodyContextListener;
import net.sourceforge.jpotpourri.gui.table.ITableFillEmptyRowsReceiver;
import net.sourceforge.jpotpourri.gui.table.TableBodyContext;
import net.sourceforge.jpotpourri.gui.table.TableEmptyRowsPainter;

import org.jdesktop.swingx.JXTable;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
class Log4jTable extends JXTable implements ITableFillEmptyRowsReceiver, ITableBodyContextListener {

	private static final long serialVersionUID = -5142437725186427922L;

	private final TableEmptyRowsPainter emptyRowsPainter;
	
	private Color colorRowBackgroundEven;
	private final Color colorRowBackgroundOdd;
	

	private final Color colorRowForeground;
	private final Color colorSelectedRowForeground;
	private final Color colorSelectedRowBackground;


    private static final String CMD_EXCEPTION_DETAILS = "CMD_EXCEPTION_DETAILS";
	
    
	public Log4jTable(final Log4jTableModel tableModel, final Log4jGuiTableDefinition tableDefinition) {
		super(tableModel);
		// this.addHighlighter() ... no, we are painting alternating rows at our own
		De.bug("Creating new Log4jTable with defintion: " + tableDefinition);
		
		if(tableDefinition.getRows() != Log4jGuiTableDefinition.DEFAULT_ROWS) {
			this.setVisibleRowCount(tableDefinition.getRows());
		}
		
		this.colorRowBackgroundEven = tableDefinition.getColorRowBackgroundEven();
		this.colorRowBackgroundOdd = tableDefinition.getColorRowBackgroundOdd();
		
		this.colorRowForeground = tableDefinition.getColorRowForeground();
		this.colorSelectedRowForeground = tableDefinition.getColorSelectedRowForeground();
		this.colorSelectedRowBackground = tableDefinition.getColorSelectedRowBackground();
		
		this.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        this.setColumnSelectionAllowed(false);
        this.setRowSelectionAllowed(true);
        
        this.setColumnControlVisible(true);
        this.setShowGrid(false);
        this.packAll();
        
        this.emptyRowsPainter = new TableEmptyRowsPainter(this);
        this.initContextMenu();
	}
	
	private void initContextMenu() {
		final List<JMenuItem> itemsSingle = new ArrayList<JMenuItem>();
		TableBodyContext.newJMenuItem(itemsSingle, "Exception Details", CMD_EXCEPTION_DETAILS); // optional icon
        

        final List<JMenuItem> itemsMultiple = new ArrayList<JMenuItem>();
        final JMenuItem exceptionDetailsMultiple =
        	TableBodyContext.newJMenuItem(itemsMultiple, "Exception Details", CMD_EXCEPTION_DETAILS); // optional icon
        exceptionDetailsMultiple.setEnabled(false);
        new TableBodyContext(this, itemsSingle, itemsMultiple, this);
	}

	@Override
	public final void paint(final Graphics g) {
		super.paint(g);
		this.emptyRowsPainter.delegatePaint(g);
	}

	public final Color getColorRowBackgroundEven() {
		return this.colorRowBackgroundEven;
	}

	public final Color getColorRowBackgroundOdd() {
		return this.colorRowBackgroundOdd;
	}
	


	@Override
	public final Component prepareRenderer(final TableCellRenderer renderer, final int row, final int column) {
		final Component c = super.prepareRenderer(renderer, row, column);

		final Color bgColor;
		final Color fgColor;
//        boolean focused = hasFocus();
        boolean selected = isCellSelected(row, column);
        
        if(selected) {
        	bgColor = this.colorSelectedRowBackground;
        	fgColor = this.colorSelectedRowForeground;
        } else {
        	final boolean isEven = row % 2 == 0;
        	bgColor = isEven ? this.colorRowBackgroundEven : this.colorRowBackgroundOdd;
        	fgColor = this.colorRowForeground;
        }
        c.setBackground(bgColor);
		c.setForeground(fgColor);
        
		return c;
	}

    
	public final void contextMenuClicked(final JMenuItem item, final int tableRowSelected) {
		final String cmd = item.getActionCommand();
		if(cmd.equals(CMD_EXCEPTION_DETAILS)) {
			JOptionPane.showMessageDialog(this, "Work in progress...", "Exception Details",
					JOptionPane.WARNING_MESSAGE); // FIXME exception details
		} else {
			throw new IllegalArgumentException("actionCommand=" + cmd);
		}
	}


	public final void contextMenuClickedMultiple(final JMenuItem item, final int[] tableRowsSelected) {
		final String cmd = item.getActionCommand();
		if(cmd.equals(CMD_EXCEPTION_DETAILS)) {
			assert(false); // may not happen
		} else {
			throw new IllegalArgumentException("actionCommand=" + cmd);
		}
	}

}
