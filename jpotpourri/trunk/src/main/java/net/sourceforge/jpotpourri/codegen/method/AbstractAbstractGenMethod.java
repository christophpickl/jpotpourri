package net.sourceforge.jpotpourri.codegen.method;

import java.util.List;

import net.sourceforge.jpotpourri.codegen.GenArgument;
import net.sourceforge.jpotpourri.codegen.GenVisibility;
import net.sourceforge.jpotpourri.codegen.modifier.GenMethodModifier;

public abstract class AbstractAbstractGenMethod extends AbstractGenMethod {

	public AbstractAbstractGenMethod(
			final GenVisibility visibility,
			final String methodName,
			final String returnType,
			final List<GenArgument> arguments
			) {
		super(visibility, methodName, returnType, arguments, GenMethodModifier.ABSTRACT);
	}

	@Override
	protected final String getBody() {
		return null;
	}

}
