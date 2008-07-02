package net.sourceforge.jpotpourri.gui.log4jlog.gui;

import javax.swing.JComboBox;

import net.sourceforge.jpotpourri.gui.log4jlog.AllLogLevels;

import org.apache.log4j.Level;

class LogLevelFilterBox extends JComboBox {

	private static final long serialVersionUID = 4527119967934749051L;
	
	public LogLevelFilterBox() {
		super(AllLogLevels.LEVELS.toArray());
		this.setSelectedIndex(this.getItemCount() - 1);
	}
	
	public Level getSelectedLevel() {
		return (Level) this.getSelectedItem();
	}
	
}