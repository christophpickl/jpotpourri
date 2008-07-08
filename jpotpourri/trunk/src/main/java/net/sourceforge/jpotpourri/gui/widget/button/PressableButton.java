package net.sourceforge.jpotpourri.gui.widget.button;

import javax.swing.Icon;
import javax.swing.JButton;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
public class PressableButton extends JButton {

	private static final long serialVersionUID = 1784844960026759904L;

	public PressableButton(final Icon iconNormal, final Icon iconPressed) {
		super(iconNormal);
		this.setPressedIcon(iconPressed);

//		this.setRolloverEnabled(true);
//		this.setRolloverIcon(iconPressed);

		this.setBorderPainted(false);
		this.setFocusPainted(false);
		this.setContentAreaFilled(false);
	}

}

