package net.sourceforge.jpotpourri.codegen.method;

import java.util.ArrayList;

import net.sourceforge.jpotpourri.codegen.GenArgument;
import net.sourceforge.jpotpourri.codegen.GenVisibility;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
public final class NullGenConstructor extends AbstractGenConstructor {

	public NullGenConstructor() {
		super(GenVisibility.PRIVATE, "", new ArrayList<GenArgument>(0)); // not of any use
	}

	@Override
	protected String getBody() {
		return null;
	}

}
