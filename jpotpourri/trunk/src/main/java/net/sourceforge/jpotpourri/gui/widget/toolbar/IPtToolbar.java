package net.sourceforge.jpotpourri.gui.widget.toolbar;

import javax.swing.JComponent;
/**
 * 
 * @author christoph_pickl@users.sourceforge.net
 */
public interface IPtToolbar {
	
	JComponent asJComponent();
	
	
	boolean addIPtToolbarListener(final IPtToolbarListener listener);
	
	boolean removeIPtToolbarListener(final IPtToolbarListener listener);
}
