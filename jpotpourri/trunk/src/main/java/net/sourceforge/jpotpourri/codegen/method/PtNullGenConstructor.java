package net.sourceforge.jpotpourri.codegen.method;

import java.util.ArrayList;

import net.sourceforge.jpotpourri.codegen.PtGenArgument;
import net.sourceforge.jpotpourri.codegen.PtGenVisibility;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
public final class PtNullGenConstructor extends PtAbstractGenConstructor {

	public PtNullGenConstructor() {
		super(PtGenVisibility.PRIVATE, "", new ArrayList<PtGenArgument>(0)); // not of any use
	}

	@Override
	protected String getBody() {
		return null;
	}

}
