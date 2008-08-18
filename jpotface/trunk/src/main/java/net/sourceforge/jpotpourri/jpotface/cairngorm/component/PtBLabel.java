package net.sourceforge.jpotpourri.jpotface.cairngorm.component;

import javax.swing.JLabel;

import net.sourceforge.jpotpourri.jpotface.cairngorm.IPtBindingListener;
import net.sourceforge.jpotpourri.jpotface.cairngorm.bindobj.PtBindableString;
import net.sourceforge.jpotpourri.jpotface.cairngorm.event.PtBindingEvent;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
public class PtBLabel implements IPtBindingListener<String> {

	private final JLabel label;
	
	public PtBLabel(final JLabel label, final PtBindableString string) {
		this.label = label;
		this.label.setText(string.getValue());
		
		string.addBindingListener(this);
	}

	public void receiveEvent(final PtBindingEvent<String> event) {
		this.label.setText(event.getNewValue());
	}
	
}
