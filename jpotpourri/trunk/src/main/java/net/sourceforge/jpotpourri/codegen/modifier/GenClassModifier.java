package net.sourceforge.jpotpourri.codegen.modifier;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
public final class GenClassModifier extends AbstractGenModifier {

	public static final GenClassModifier STATIC   = new GenClassModifier("static ");
	public static final GenClassModifier FINAL    = new GenClassModifier("final ");
	public static final GenClassModifier ABSTRACT = new GenClassModifier("abstract ");
	
	private GenClassModifier(final String code) {
		super(code);
	}
}

