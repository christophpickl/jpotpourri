package net.sourceforge.jpotpourri.jpotface.log4jlog.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.TableCellRenderer;

import net.sourceforge.jpotpourri.jpotface.log4jlog.De;
import net.sourceforge.jpotpourri.jpotface.log4jlog.PtLog4jEvent;
import net.sourceforge.jpotpourri.jpotface.log4jlog.PtLogGuiTableDefinition;
import net.sourceforge.jpotpourri.jpotface.table.IPtTableBodyContextListener;
import net.sourceforge.jpotpourri.jpotface.table.IPtTableFillEmptyRowsReceiver;
import net.sourceforge.jpotpourri.jpotface.table.PtTableBodyContext;
import net.sourceforge.jpotpourri.jpotface.table.PtTableEmptyRowsPainter;
import net.sourceforge.jpotpourri.util.PtStringUtil;

import org.apache.log4j.Level;
import org.jdesktop.swingx.JXTable;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
class LogTable extends JXTable implements IPtTableFillEmptyRowsReceiver, IPtTableBodyContextListener {

	private static final long serialVersionUID = -5142437725186427922L;

	private final transient PtTableEmptyRowsPainter emptyRowsPainter;

	private static final Color COLOR_BG_ROW_WARN = Color.YELLOW;
	private static final Color COLOR_BG_ROW_ERROR = Color.RED;
	private static final Color COLOR_BG_ROW_FATAL = new Color(187, 74, 207);

    private static final String CMD_SHOW_DETAILS = "CMD_SHOW_DETAILS";
    
	
	private Color colorRowBackgroundEven;
	private final Color colorRowBackgroundOdd;

	private final Color colorRowForeground;
	private final Color colorSelectedRowForeground;
	private final Color colorSelectedRowBackground;

	
    
	public LogTable(final LogTableModel tableModel, final PtLogGuiTableDefinition tableDefinition) {
		super(tableModel);
		// this.addHighlighter() ... no, we are painting alternating rows at our own
		De.bug("Creating new Log4jTable with defintion: " + tableDefinition);
		
		if(tableDefinition.getRows() != PtLogGuiTableDefinition.DEFAULT_ROWS) {
			this.setVisibleRowCount(tableDefinition.getRows());
		}
		
		this.colorRowBackgroundEven = tableDefinition.getColorRowBackgroundEven();
		this.colorRowBackgroundOdd = tableDefinition.getColorRowBackgroundOdd();
		
		this.colorRowForeground = tableDefinition.getColorRowForeground();
		this.colorSelectedRowForeground = tableDefinition.getColorSelectedRowForeground();
		this.colorSelectedRowBackground = tableDefinition.getColorSelectedRowBackground();
		
		// set log level comparator
		this.getColumnExt(LogTableModel.getColumnIdentifier(LogTableColumn.LEVEL)).
			setComparator(new Comparator<String>() {
			public int compare(final String o1, final String o2) {
				final Level l1 = Level.toLevel(o1);
				final Level l2 = Level.toLevel(o2);
				return l1.toInt() - l2.toInt();
			}
	    });

        // adjust column width
        this.getColumnExt(LogTableModel.getColumnIdentifier(LogTableColumn.LEVEL)).setMaxWidth(44);
        this.getColumnExt(LogTableModel.getColumnIdentifier(LogTableColumn.LEVEL)).setMinWidth(44);
        this.getColumnExt(LogTableModel.getColumnIdentifier(LogTableColumn.DATE)).setMaxWidth(130);
        this.getColumnExt(LogTableModel.getColumnIdentifier(LogTableColumn.DATE)).setMinWidth(130);
//        this.getColumnExt(Log4jTableModel.getColumnIdentifier(LogTableColumn.CLASS)).setPreferredWidth(80);
//        this.getColumnExt(Log4jTableModel.getColumnIdentifier(LogTableColumn.THREAD)).setPreferredWidth(60);
        
		// hide some columns
		this.getColumnExt(LogTableModel.getColumnIdentifier(LogTableColumn.METHOD)).setVisible(false);
		this.getColumnExt(LogTableModel.getColumnIdentifier(LogTableColumn.THREAD)).setVisible(false);
		this.getColumnExt(LogTableModel.getColumnIdentifier(LogTableColumn.EXCEPTION)).setVisible(false);
		
		this.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        this.setColumnSelectionAllowed(false);
        this.setRowSelectionAllowed(true);
        
        this.setColumnControlVisible(true);
        this.setShowGrid(false);

        this.packAll();
        
        this.emptyRowsPainter = new PtTableEmptyRowsPainter(this);
        this.initContextMenu();
        this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(final MouseEvent event) {
				if(event.getClickCount() >= 2) {
					if(getSelectedRowCount() == 1) {
						doShowDetails(getSelectedRow());
					} else {
						Toolkit.getDefaultToolkit().beep();
					}
				}
			}
        });
	}
	
	private void initContextMenu() {
		final List<JMenuItem> itemsSingle = new ArrayList<JMenuItem>();
		PtTableBodyContext.newJMenuItem(itemsSingle, "Show Details", CMD_SHOW_DETAILS); // optional icon
        

        final List<JMenuItem> itemsMultiple = new ArrayList<JMenuItem>();
        final JMenuItem exceptionDetailsMultiple =
        	PtTableBodyContext.newJMenuItem(itemsMultiple, "Show Details", CMD_SHOW_DETAILS); // optional icon
        exceptionDetailsMultiple.setEnabled(false);
        new PtTableBodyContext(this, itemsSingle, itemsMultiple, this);
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
        	final PtLog4jEvent event = this.getProperModel().getEventAt(viewRow);
        	final Level level = event.getLevel();
        	
        	if(level == Level.WARN) {
        		bgColor = COLOR_BG_ROW_WARN;
        	} else if(level == Level.ERROR) {
        		bgColor = COLOR_BG_ROW_ERROR;
        	} else if(level == Level.FATAL) {
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

	private LogTableModel getProperModel() {
		return (LogTableModel) this.getModel();
	}
    
	public final void contextMenuClicked(final JMenuItem item, final int tableRowSelected) {
		final String cmd = item.getActionCommand();
		if(cmd.equals(CMD_SHOW_DETAILS)) {
			this.doShowDetails(tableRowSelected);
		} else {
			throw new IllegalArgumentException("actionCommand=" + cmd);
		}
	}
	
	private void doShowDetails(final int viewRow) {
		final int modelRow = this.convertRowIndexToModel(viewRow);
		final PtLog4jEvent event = this.getProperModel().getEventAt(modelRow);
		new LogEventDialog(event).setVisible(true);
	}


	public final void contextMenuClickedMultiple(final JMenuItem item, final int[] tableRowsSelected) {
		final String cmd = item.getActionCommand();
		if(cmd.equals(CMD_SHOW_DETAILS)) {
			assert(false); // may not happen
		} else {
			throw new IllegalArgumentException("actionCommand=" + cmd);
		}
	}


	/**
	 * @author christoph_pickl@users.sourceforge.net
	 */
	private static class LogEventDialog extends JDialog {
		
		private static final long serialVersionUID = 5266842801576134828L;
		
		private static final Font LABEL_FONT = new Font(null, Font.BOLD, 12);
		
		private static final Insets INSET_LEFT = new Insets(0, 0, 2, 3);
		private static final Insets INSET_RIGHT = new Insets(0, 0, 2, 0);
		
		private final PtLog4jEvent event;
		
		public LogEventDialog(final PtLog4jEvent event) {
			this.event = event;
			this.setTitle("Log Event Details");
			this.getContentPane().add(this.initComponents());
			this.pack();
			this.setLocationRelativeTo(null);
		}
		
		private JPanel initComponents() {
			final JPanel panel = new JPanel();
			panel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
			final GridBagLayout layout = new GridBagLayout();
			final GridBagConstraints c = new GridBagConstraints();
			layout.setConstraints(panel, c);
			panel.setLayout(layout);
			
			c.anchor = GridBagConstraints.FIRST_LINE_START;
			c.gridx = 0;
			c.gridy = 0;

			this.addRow("Date", LogTableColumn.DATE_FORMAT.format(this.event.getDate()), c, panel);
			this.addRow("Level", this.event.getLevel().toString(), c, panel);
			this.addRow("Class", this.event.getLogClassName(), c, panel);
			this.addRow("Method", this.event.getLogMethod(), c, panel);
			this.addRow("Thread", this.event.getThreadName(), c, panel);
			final JTextArea textAreaMessage = new JTextArea(this.event.getMessageRendered(), 3, 60);
			textAreaMessage.setEditable(false);
			this.addRow("Message", new JScrollPane(textAreaMessage), c, panel, 0.0, 0.3, GridBagConstraints.BOTH);
			final String exceptionString = this.event.getThrowable() != null ?
					PtStringUtil.convertExceptionToString(this.event.getThrowable(), true) :
					"N/A";
			final JTextArea textAreaException = new JTextArea(exceptionString, 3, 60);
			textAreaException.setEditable(false);
			this.addRow("Exception", new JScrollPane(textAreaException), c, panel, 0.0, 0.3, GridBagConstraints.BOTH);
			
			return panel;
		}
		
		private void addRow(final String label, final String value, final GridBagConstraints c, final JPanel panel) {
			this.addRow(label, new JLabel(value), c, panel, 0.0, 0.0, GridBagConstraints.NONE);
		}
		
		private void addRow(final String label, final JComponent comp, final GridBagConstraints c, final JPanel panel,
				final double weighty1, final double weighty2, final int fillMode) {
			
			c.fill = GridBagConstraints.NONE;
			c.weightx = 0.0;
			c.weighty = weighty1;
			c.gridx = 0;
			c.gridy++;
			final JLabel lbl = new JLabel(label);
			lbl.setFont(LABEL_FONT);
			c.insets = INSET_LEFT;
			panel.add(lbl, c);
			
			c.fill = fillMode;
			c.weightx = 1.0;
			c.weighty = weighty2;
			c.gridx = 1;
			c.insets = INSET_RIGHT;
			panel.add(comp, c);
		}
	}
	
}
