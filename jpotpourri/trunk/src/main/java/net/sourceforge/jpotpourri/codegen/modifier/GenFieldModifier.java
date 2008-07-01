package net.sourceforge.jpotpourri.codegen.modifier;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
public final class GenFieldModifier extends AbstractGenModifier {

	public static final GenFieldModifier STATIC = new GenFieldModifier("static ");
	public static final GenFieldModifier FINAL  = new GenFieldModifier("final ");
	
	private GenFieldModifier(final String code) {
		super(code);
	}
}
