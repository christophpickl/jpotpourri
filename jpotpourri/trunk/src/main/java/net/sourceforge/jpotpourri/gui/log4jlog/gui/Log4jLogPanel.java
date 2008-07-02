package net.sourceforge.jpotpourri.gui.log4jlog.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import net.sourceforge.jpotpourri.gui.inputfield.search.DefaultSearchFieldListener;
import net.sourceforge.jpotpourri.gui.inputfield.search.IDefaultSearchFieldListener;
import net.sourceforge.jpotpourri.gui.log4jlog.Log4jEvent;
import net.sourceforge.jpotpourri.gui.log4jlog.Log4jGuiHandlerDefinition;
import net.sourceforge.jpotpourri.gui.log4jlog.TableFilter;
import net.sourceforge.jpotpourri.gui.log4jlog.gui.table.Log4jTable;
import net.sourceforge.jpotpourri.gui.log4jlog.gui.table.Log4jTableModel;

public class Log4jLogPanel extends JPanel implements IDefaultSearchFieldListener {

	private static final long serialVersionUID = -2803278698478385285L;

	
	private final LogLevelFilterBox logLevelFilterBox = new LogLevelFilterBox();
	private final LogSearchField searchField = new LogSearchField();
	
	private final Log4jTable table;
	private final Log4jTableModel tableModel = new Log4jTableModel();
	
	
	
	
	public Log4jLogPanel(Log4jGuiHandlerDefinition handlerDefinition) {
		this.table = new Log4jTable(handlerDefinition.getTableDefinition());
		this.table.setModel(this.tableModel);
		
		final JScrollPane scrollPane = new JScrollPane(this.table);
		this.setLayout(new BorderLayout());
		this.add(this.newFilterPanel(), BorderLayout.NORTH);
		this.add(scrollPane, BorderLayout.CENTER);
	}

	
	public void processLoggingEvent(final Log4jEvent event) {
		this.tableModel.addLoggingEvent(event);
	}


	public void doResetSearch() {
		this.tableModel.doFilter(TableFilter.FILTER_NONE);
	}

	public void doSearch(final String s) {
		final String p = this.searchField.getProperText();
		if(s == null || p == null) {
			assert(s == null && p == null);
		} else {
			assert(s != null && p != null && s.equals(p)); // TODO asserts
		}
		this.tableModel.doFilter(new TableFilter(this.logLevelFilterBox.getSelectedLevel(), s));
	}
	
	
	
	
	
	
	private JPanel newFilterPanel() {
		final JPanel panel = new JPanel();

		this.logLevelFilterBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doLogLevelFilterBoxChanged();
			}
			
		});
		
		panel.add(new JLabel("Log Level"));
		panel.add(this.logLevelFilterBox);

        this.searchField.addDefaultSearchFieldListener(this);
        
		panel.add(searchField);
		
		return panel;
	}
	
	
	
	private void doLogLevelFilterBoxChanged() {
		final TableFilter filter = new TableFilter(this.logLevelFilterBox.getSelectedLevel(), this.searchField.getProperText());
		this.tableModel.doFilter(filter);
	}
	
}
