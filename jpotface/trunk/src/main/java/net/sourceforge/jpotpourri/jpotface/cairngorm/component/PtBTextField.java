package net.sourceforge.jpotpourri.jpotface.cairngorm.component;

import javax.swing.JTextField;

import net.sourceforge.jpotpourri.jpotface.cairngorm.bindobj.PtBindableString;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
public class PtBTextField extends PtBTextFieldGeneric<String, PtBindableString> {

	public PtBTextField(
			final JTextField textField,
			final PtBindableString string
		) {
		super(textField, string);
	}
}
