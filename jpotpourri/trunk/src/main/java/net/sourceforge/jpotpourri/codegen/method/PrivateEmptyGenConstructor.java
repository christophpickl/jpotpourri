package net.sourceforge.jpotpourri.codegen.method;

import net.sourceforge.jpotpourri.codegen.GenVisibility;

/**
 * 
 * @author christoph_pickl@users.sourceforge.net
 */
public class PrivateEmptyGenConstructor extends AbstractGenConstructor {

	public PrivateEmptyGenConstructor(final String className) {
		super(GenVisibility.PRIVATE, className);
	}

	@Override
	protected final String getBody() {
		return "// no instantiation (singleton/utility)";
	}

}
