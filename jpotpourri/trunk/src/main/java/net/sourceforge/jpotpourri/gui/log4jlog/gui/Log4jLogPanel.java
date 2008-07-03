package net.sourceforge.jpotpourri.gui.log4jlog.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import net.sourceforge.jpotpourri.gui.inputfield.search.IDefaultSearchFieldListener;
import net.sourceforge.jpotpourri.gui.log4jlog.Log4jEvent;
import net.sourceforge.jpotpourri.gui.log4jlog.Log4jGuiHandlerDefinition;
import net.sourceforge.jpotpourri.gui.log4jlog.TableFilter;
import net.sourceforge.jpotpourri.gui.log4jlog.gui.table.Log4jTable;
import net.sourceforge.jpotpourri.gui.log4jlog.gui.table.Log4jTableModel;

import org.apache.log4j.Level;

public class Log4jLogPanel extends JPanel implements IDefaultSearchFieldListener {

	private static final long serialVersionUID = -2803278698478385285L;

	
	private final LogLevelFilterBox logLevelFilterBox = new LogLevelFilterBox(Level.ERROR);
	private final LogSearchField searchField = new LogSearchField();
	
	private final Log4jTable table;
	private final Log4jTableModel tableModel = new Log4jTableModel();

	
	private TableFilter tableFilter;
	
	
	
	public Log4jLogPanel(Log4jGuiHandlerDefinition handlerDefinition) {
		this.table = new Log4jTable(this.tableModel, handlerDefinition.getTableDefinition());
		
		// init gui
		final JScrollPane scrollPane = new JScrollPane(this.table);
		this.setLayout(new BorderLayout());
		this.add(this.newFilterPanel(), BorderLayout.NORTH);
		this.add(scrollPane, BorderLayout.CENTER);
	}

	
	public void processLoggingEvent(final Log4jEvent event) {
		this.tableModel.addLoggingEvent(event);
	}


	public void doResetSearch() {
		final TableFilter properFilter;
		if(this.tableFilter == null) {
			properFilter = TableFilter.FILTER_NONE;
		} else {
			this.tableFilter = TableFilter.newBySearchString(this.tableFilter, null);
			properFilter = this.tableFilter;
		}
		this.tableModel.doFilter(properFilter);
	}

	public void doSearch(final String s) {
		final String p = this.searchField.getProperText();
		if(s == null || p == null) {
			assert(s == null && p == null);
		} else {
			assert(s.equals(p)); // TODO asserts
		}
		
		this.tableFilter = new TableFilter(this.logLevelFilterBox.getSelectedLevel(), s);
		this.tableModel.doFilter(this.tableFilter);
	}
	
	
	
	
	
	
	private JPanel newFilterPanel() {
		final JPanel panel = new JPanel(new BorderLayout());

		this.logLevelFilterBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doLogLevelFilterBoxChanged();
			}
		});
		this.doLogLevelFilterBoxChanged(); // apply initial filter
		
		final JPanel panelWest = new JPanel();
		panelWest.add(new JLabel("Log Level"));
		panelWest.add(this.logLevelFilterBox);
		panel.add(panelWest, BorderLayout.WEST);

        this.searchField.addDefaultSearchFieldListener(this);
        
        final JPanel searchFieldPanel = new JPanel();
        searchFieldPanel.add(this.searchField);
		panel.add(searchFieldPanel, BorderLayout.EAST);
		
		return panel;
	}
	
	private void doLogLevelFilterBoxChanged() {
		this.tableFilter = new TableFilter(this.logLevelFilterBox.getSelectedLevel(), this.searchField.getProperText());
		this.tableModel.doFilter(this.tableFilter);
	}
	
}
