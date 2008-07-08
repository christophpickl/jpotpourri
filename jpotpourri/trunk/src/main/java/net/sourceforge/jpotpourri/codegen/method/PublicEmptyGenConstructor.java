package net.sourceforge.jpotpourri.codegen.method;

import net.sourceforge.jpotpourri.codegen.GenVisibility;

public class PublicEmptyGenConstructor extends AbstractGenConstructor {

	public PublicEmptyGenConstructor(final String className) {
		super(GenVisibility.PUBLIC, className);
	}

	@Override
	protected final String getBody() {
		return "// nothing to do";
	}

}