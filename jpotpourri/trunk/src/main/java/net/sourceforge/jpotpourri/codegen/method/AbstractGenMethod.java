package net.sourceforge.jpotpourri.codegen.method;

import java.util.ArrayList;
import java.util.List;

import net.sourceforge.jpotpourri.codegen.GenArgument;
import net.sourceforge.jpotpourri.codegen.GenVisibility;
import net.sourceforge.jpotpourri.codegen.modifier.GenMethodModifier;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
public abstract class AbstractGenMethod extends AbstractGenPseudoMethod {

	public AbstractGenMethod(
			final GenVisibility visibility,
			final String methodName,
			final String returnType,
			final GenMethodModifier... modifiers
			) {
		super(visibility, methodName, returnType, new ArrayList<GenArgument>(0), modifiers);
		// nothing else to do
	}
	
	public AbstractGenMethod(
			final GenVisibility visibility,
			final String methodName,
			final String returnType,
			final List<GenArgument> arguments,
			final GenMethodModifier... modifiers
			) {
		super(visibility, methodName, returnType, arguments, modifiers);
		// nothing else to do
	}

	protected final String getReturnType() {
		return this.getUnsafeReturnType();
	}
	
}
