package net.sourceforge.jpotpourri.gui;

import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
public enum GlobalKey {
	
	ESCAPE("CMD_GLOBAL_KEY_ESCAPE", KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0)),
	SPACE("CMD_GLOBAL_KEY_SPACE", KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0));
	
	private final String actionCommand;
	private final KeyStroke keyStroke;
	
	
	private GlobalKey(final String actionCommand, final KeyStroke keyStroke) {
		this.actionCommand = actionCommand;
		this.keyStroke = keyStroke;
	}
	
	public String getActionCommand() {
		return this.actionCommand;
	}
	
	public KeyStroke getKeyStroke() {
		return this.keyStroke;
	}
}
