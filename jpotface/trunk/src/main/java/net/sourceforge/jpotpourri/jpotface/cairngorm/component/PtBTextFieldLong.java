package net.sourceforge.jpotpourri.jpotface.cairngorm.component;

import javax.swing.JTextField;

import net.sourceforge.jpotpourri.jpotface.cairngorm.bindobj.PtBindableLong;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
public class PtBTextFieldLong extends PtBTextFieldGeneric<Long, PtBindableLong> {

	public PtBTextFieldLong(
			final JTextField textField,
			final PtBindableLong value
		) {
		super(textField, value);
	}
}
