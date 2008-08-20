package net.sourceforge.jpotpourri.jpotface.cairngorm.component;

import net.sourceforge.jpotpourri.jpotface.cairngorm.IPtBindingListener;
import net.sourceforge.jpotpourri.jpotface.cairngorm.bindobj.PtBindableLong;
import net.sourceforge.jpotpourri.jpotface.cairngorm.event.PtBindingEvent;
import net.sourceforge.jpotpourri.jpotface.inputfield.PtTimestampField;
import net.sourceforge.jpotpourri.util.PtDateUtil;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
public class PtBTimestampField implements IPtBindingListener<Long> {

	private final PtTimestampField field;
	
	public PtBTimestampField(final PtTimestampField label, final PtBindableLong value) {
		this.field = label;
		this.field.setText(PtDateUtil.formatTimestamp(value.getValue().longValue()));
		
		value.addBindingListener(this);
	}

	public void receiveEvent(final PtBindingEvent<Long> event) {
		this.field.setText(PtDateUtil.formatTimestamp(event.getNewValue().longValue()));
	}
}
