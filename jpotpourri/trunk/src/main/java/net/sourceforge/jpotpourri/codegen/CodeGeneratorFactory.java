package net.sourceforge.jpotpourri.codegen;

import java.io.File;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
public final class CodeGeneratorFactory {

	private CodeGeneratorFactory() {
		// no instantiation
	}
	
	public static ICodeGenerator newCodeGenerator(final File sourceFolder) {
		return new CodeGenerator(sourceFolder);
	}
}
