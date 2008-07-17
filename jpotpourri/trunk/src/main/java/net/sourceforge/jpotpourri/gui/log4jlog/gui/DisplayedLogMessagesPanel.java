package net.sourceforge.jpotpourri.gui.log4jlog.gui;

import javax.swing.JComponent;
import javax.swing.JLabel;

import net.sourceforge.jpotpourri.gui.log4jlog.IDisplayedLogMessages;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
class DisplayedLogMessagesPanel implements IDisplayedLogMessages {

	private final JLabel label = new JLabel("-/-");
	
	
	public JComponent getComponent() {
		return this.label;
	}


	public void displayedLogMessagesChanged(
			final int displayedMessages,
			final int totalMessages) {
		// TODO Auto-generated method stub
		this.label.setText(displayedMessages + "/" + totalMessages);
	}
	
}
