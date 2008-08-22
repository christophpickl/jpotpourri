package net.sourceforge.jpotpourri.jcairngorm.bindobj;


/**
 * @author christoph_pickl@users.sourceforge.net
 */
public class PtBindableString extends PtAbstractBindableSimpleObject<String> implements IPtBindableStringLike<String> {
	
	public PtBindableString(final String string) {
		this(string, "__String");
	}
	
	public PtBindableString(final String value, final String propertyName) {
		super(value, propertyName);
	}

	public final String getAsString() {
		return this.getValue();
	}

	public final void setAsString(final String value) {
		this.setValue(value);
	}
	
}
