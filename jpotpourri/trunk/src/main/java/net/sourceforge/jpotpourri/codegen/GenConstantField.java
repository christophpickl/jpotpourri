package net.sourceforge.jpotpourri.codegen;

import net.sourceforge.jpotpourri.codegen.modifier.GenFieldModifier;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
public final class GenConstantField extends GenField {

	private GenConstantField(final GenVisibility visibility, final String fieldType, final String fieldName,
			final String initValue, final GenFieldModifier... modifiers) {
		super(visibility, fieldType, fieldName, initValue, modifiers);
		if(initValue == null) {
			throw new NullPointerException("initValue");
		}
	}
	
	public static GenConstantField newPublicConstant(final String fieldType, final String fieldName, 
			final String initValue) {
		return new GenConstantField(GenVisibility.PUBLIC, fieldType, fieldName, initValue,
				GenFieldModifier.STATIC, GenFieldModifier.FINAL);
	}
	
	public static GenConstantField newPrivateConstant(final String fieldType, final String fieldName, 
			final String initValue) {
		return new GenConstantField(GenVisibility.PRIVATE, fieldType, fieldName, initValue,
				GenFieldModifier.STATIC, GenFieldModifier.FINAL);
	}
}
