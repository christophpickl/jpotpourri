package net.sourceforge.jpotpourri.gui.log4jlog.gui;

import javax.swing.JComponent;
import javax.swing.JLabel;

import net.sourceforge.jpotpourri.gui.log4jlog.IDisplayedLogMessages;

class DisplayedLogMessagesPanel implements IDisplayedLogMessages {

	private final JLabel label = new JLabel("-/-");
	
	
	public JComponent getComponent() {
		return this.label;
	}


	public void displayedLogMessagesChanged(
			int displayedMessages,
			int totalMessages) {
		// TODO Auto-generated method stub
		this.label.setText(displayedMessages + "/" + totalMessages);
	}
	
}
