package net.sourceforge.jpotpourri.codegen;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
public interface ICodeGenerator {

	void process(final AbstractGenClass genClass) throws CodeGeneratorException;
	
}
