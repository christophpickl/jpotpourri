package net.sourceforge.jpotpourri.codegen;

import net.sourceforge.jpotpourri.codegen.modifier.GenFieldModifier;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
public class GenField implements IJavaCode {
	
	private final GenVisibility visibility;
	private final String fieldType;
	private final String fieldName;
	/** eg: "new SomeClass()". */
	private final String initValue;
	private final GenFieldModifier[] modifiers;


	public GenField(final GenVisibility visibility, final String fieldType, final String fieldName,
			final GenFieldModifier... modifiers) {
		this(visibility, fieldType, fieldName, null, modifiers);
	}
	public GenField(final GenVisibility visibility, final String fieldType, final String fieldName,
			final String initValue, final GenFieldModifier... modifiers) {
		this.visibility = visibility;
		this.fieldType = fieldType;
		this.fieldName = fieldName;
		this.initValue = initValue;
		this.modifiers = modifiers;
	}

	public final String toCode() {
		final StringBuilder sb = new StringBuilder();
		
		sb.append("\t").append(this.visibility.toCode());
		
		for (GenFieldModifier modifier : this.modifiers) {
			sb.append(modifier.toCode());
		}
		
		sb.append(this.fieldType).append(" ");
		
		sb.append(this.fieldName);
		
		if(this.initValue != null) {
			sb.append(" = ").append(this.initValue);
		}
		
		sb.append(";\n");
		
		return sb.toString();
	}

}
