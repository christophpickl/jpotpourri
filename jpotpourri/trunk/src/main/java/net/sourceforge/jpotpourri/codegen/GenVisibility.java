package net.sourceforge.jpotpourri.codegen;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
public final class GenVisibility implements IJavaCode {
	
	public static final GenVisibility PUBLIC = new GenVisibility("public ");
	public static final GenVisibility PROTECTED = new GenVisibility("protected ");
	public static final GenVisibility DEFAULT = new GenVisibility("");
	public static final GenVisibility PRIVATE = new GenVisibility("private ");
	
	private final String code;
	
	
	private GenVisibility(final String code) {
		this.code = code;
	}
	
	public String toCode() {
		return this.code;
	}

	@Override
	public String toString() {
		return this.code;
	}
}
