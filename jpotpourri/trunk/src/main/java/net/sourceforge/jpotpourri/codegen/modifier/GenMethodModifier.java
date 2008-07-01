package net.sourceforge.jpotpourri.codegen.modifier;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
public final class GenMethodModifier extends AbstractGenModifier {

	public static final GenMethodModifier STATIC   = new GenMethodModifier("static ");
	public static final GenMethodModifier FINAL    = new GenMethodModifier("final ");
	public static final GenMethodModifier ABSTRACT = new GenMethodModifier("abstract ");
	
	private GenMethodModifier(final String code) {
		super(code);
	}
}
