package net.sourceforge.jpotpourri.codegen;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
public class CodeGeneratorException extends Exception {

	private static final long serialVersionUID = 2661065520604650162L;

	public CodeGeneratorException(final String msg) {
		super(msg);
	}

	public CodeGeneratorException(final String msg, final Exception cause) {
		super(msg, cause);
	}
	
}
