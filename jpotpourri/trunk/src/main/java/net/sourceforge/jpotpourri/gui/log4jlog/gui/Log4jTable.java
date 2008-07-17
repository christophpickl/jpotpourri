package net.sourceforge.jpotpourri.gui.log4jlog.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Comparator;
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

import org.apache.log4j.Level;
import org.jdesktop.swingx.JXTable;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
class Log4jTable extends JXTable implements ITableFillEmptyRowsReceiver, ITableBodyContextListener {

	private static final long serialVersionUID = -5142437725186427922L;

	private final transient TableEmptyRowsPainter emptyRowsPainter;

	private static final Color COLOR_BG_ROW_WARN = Color.YELLOW;
	private static final Color COLOR_BG_ROW_ERROR = Color.ORANGE;
	private static final Color COLOR_BG_ROW_FATAL = Color.RED;

    private static final String CMD_EXCEPTION_DETAILS = "CMD_EXCEPTION_DETAILS";
    
	
	private Color colorRowBackgroundEven;
	private final Color colorRowBackgroundOdd;

	private final Color colorRowForeground;
	private final Color colorSelectedRowForeground;
	private final Color colorSelectedRowBackground;

	
    
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
		
		// set log level comparator
		this.getColumnExt(Log4jTableModel.getColumnIdentifier(LogTableColumn.LEVEL)).
			setComparator(new Comparator<String>() {
			public int compare(final String o1, final String o2) {
				final Level l1 = Level.toLevel(o1);
				final Level l2 = Level.toLevel(o2);
				return l1.toInt() - l2.toInt();
			}
	    });

        // adjust column width
        this.getColumnExt(Log4jTableModel.getColumnIdentifier(LogTableColumn.LEVEL)).setMaxWidth(44);
        this.getColumnExt(Log4jTableModel.getColumnIdentifier(LogTableColumn.LEVEL)).setMinWidth(44);
        this.getColumnExt(Log4jTableModel.getColumnIdentifier(LogTableColumn.DATE)).setMaxWidth(130);
        this.getColumnExt(Log4jTableModel.getColumnIdentifier(LogTableColumn.DATE)).setMinWidth(130);
//        this.getColumnExt(Log4jTableModel.getColumnIdentifier(LogTableColumn.CLASS)).setPreferredWidth(80);
//        this.getColumnExt(Log4jTableModel.getColumnIdentifier(LogTableColumn.THREAD)).setPreferredWidth(60);
        
		// hide some columns
		this.getColumnExt(Log4jTableModel.getColumnIdentifier(LogTableColumn.METHOD)).setVisible(false);
		this.getColumnExt(Log4jTableModel.getColumnIdentifier(LogTableColumn.THREAD)).setVisible(false);
		this.getColumnExt(Log4jTableModel.getColumnIdentifier(LogTableColumn.EXCEPTION)).setVisible(false);
		
		this.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        this.setColumnSelectionAllowed(false);
        this.setRowSelectionAllowed(true);
        
        this.setColumnControlVisible(true);
        this.setShowGrid(false);

        this.packAll();
        
        this.emptyRowsPainter = new TableEmptyRowsPainter(this);
        this.initContextMenu();
	}
	// TODO enable double click on table ->
	//      pop summary dialog (content in table could be too big, especially message part)
	
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
	public final Component prepareRenderer(
			final TableCellRenderer renderer,
			final int modelRowIndex,
			final int modelColumnIndex) {
		final Component c = super.prepareRenderer(renderer, modelRowIndex, modelColumnIndex);

		final Color bgColor;
		final Color fgColor;
//        boolean focused = hasFocus();
        boolean selected = isCellSelected(modelRowIndex, modelColumnIndex);
        
        if(selected) {
        	bgColor = this.colorSelectedRowBackground;
        	fgColor = this.colorSelectedRowForeground;
        } else {
        	final boolean isEven = modelRowIndex % 2 == 0;
        	
        	final int viewRow = this.convertRowIndexToModel(modelRowIndex);
        	final int viewCol = 1; // FIXME table hack
        	String s = (String) this.getModel().getValueAt(viewRow, viewCol);
        	if("WARN".equals(s)) {
        		bgColor = COLOR_BG_ROW_WARN;
        	} else if("ERROR".equals(s)) {
        		bgColor = COLOR_BG_ROW_ERROR;
        	} else if("FATAL".equals(s)) {
        		bgColor = COLOR_BG_ROW_FATAL;
        	} else {
        		bgColor = isEven ? this.colorRowBackgroundEven : this.colorRowBackgroundOdd;
        	}
        	
        	
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
