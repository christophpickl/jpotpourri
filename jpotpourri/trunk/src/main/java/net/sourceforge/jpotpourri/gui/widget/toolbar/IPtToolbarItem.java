package net.sourceforge.jpotpourri.gui.widget.toolbar;

import javax.swing.ImageIcon;

/**
 * 
 * @author christoph_pickl@users.sourceforge.net
 */
public interface IPtToolbarItem {

	String getTooltipText();
	
	ImageIcon getImageIcon();
	
	String getActionCommand();
	
}
