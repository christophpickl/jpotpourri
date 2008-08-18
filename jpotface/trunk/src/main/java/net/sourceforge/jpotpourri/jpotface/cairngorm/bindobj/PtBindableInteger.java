package net.sourceforge.jpotpourri.jpotface.cairngorm.bindobj;


/**
 * @author christoph_pickl@users.sourceforge.net
 */
public class PtBindableInteger extends PtAbstractBindableSimpleObject<Integer> {
	
	public PtBindableInteger(final Integer value) {
		this(value, "__Integer");
	}
	
	public PtBindableInteger(final Integer value, final String propertyName) {
		super(value, propertyName);
	}
	
}
