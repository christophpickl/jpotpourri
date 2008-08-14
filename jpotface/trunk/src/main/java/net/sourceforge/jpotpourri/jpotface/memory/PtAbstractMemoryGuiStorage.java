package net.sourceforge.jpotpourri.jpotface.memory;

import javax.swing.JCheckBox;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import net.sourceforge.jpotpourri.jpotface.inputfield.PtNumberDoubleSpinner;
import net.sourceforge.jpotpourri.jpotface.inputfield.PtNumberIntegerSpinner;

/**
 * 
 * @param <T> key type
 * @author christoph_pickl@users.sourceforge.net
 */
public abstract class PtAbstractMemoryGuiStorage<T extends IPtMemoryKey<?>> extends PtAbstractMemoryStorage<T> {

//	public abstract void enableMemoryOn(
//			final T key,
//			final Object target,
//			final String getterMethodName,
//			final String setterMethodName,
//			final Class<?> propertyClass,
//			final boolean isPrimitive,
//			final Object defaultValue);
	
    // -----------------------------------------------------------------------------------------------------------------
    //  JTextField
    // -----------------------------------------------------------------------------------------------------------------
	public void enableMemoryOn(
		final T key,
		final JTextField target
		) {
		this.enableMemoryOn(key, target, "");
	}
	public void enableMemoryOn(
			final T key,
			final JTextField target,
			final String defaultValue
			) {
		this.enableMemoryOn(key, target, "getText", "setText", String.class, false, defaultValue);
	}
	

    // -----------------------------------------------------------------------------------------------------------------
    //  JCheckBox
    // -----------------------------------------------------------------------------------------------------------------
	public void enableMemoryOn(
		final T key,
		final JCheckBox target
		) {
		this.enableMemoryOn(key, target, false);
	}
	public void enableMemoryOn(
			final T key,
			final JCheckBox target,
			final boolean defaultValue
			) {
		this.enableMemoryOn(key, target, "isSelected", "setSelected", Boolean.class, true, defaultValue);
	}
	
	// TODO JRadioButton (ButtonGroup?)

    // -----------------------------------------------------------------------------------------------------------------
    //  JTabbedPane
    // -----------------------------------------------------------------------------------------------------------------
	public void enableMemoryOn(
		final T key,
		final JTabbedPane target
		) {
		this.enableMemoryOn(key, target, 0);
	}
	public void enableMemoryOn(
			final T key,
			final JTabbedPane target,
			final int defaultValue
			) {
		this.enableMemoryOn(key, target, "getSelectedIndex", "setSelectedIndex", int.class, true, defaultValue);
	}
	

    // -----------------------------------------------------------------------------------------------------------------
    //  PtNumberIntegerSpinner
    // -----------------------------------------------------------------------------------------------------------------
	public void enableMemoryOn(
		final T key,
		final PtNumberIntegerSpinner target
		) {
		this.enableMemoryOn(key, target, 0);
	}
	public void enableMemoryOn(
			final T key,
			final PtNumberIntegerSpinner target,
			final int defaultValue
			) {
		this.enableMemoryOn(key, target, "getNumber", "setNumber", int.class, true, defaultValue);
	}
	

    // -----------------------------------------------------------------------------------------------------------------
    //  PtNumberDoubleSpinner
    // -----------------------------------------------------------------------------------------------------------------
	public void enableMemoryOn(
		final T key,
		final PtNumberDoubleSpinner target
		) {
		this.enableMemoryOn(key, target, 0);
	}
	public void enableMemoryOn(
			final T key,
			final PtNumberDoubleSpinner target,
			final int defaultValue
			) {
		this.enableMemoryOn(key, target, "getNumber", "setNumber", double.class, true, defaultValue);
	}
}
