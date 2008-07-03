package net.sourceforge.jpotpourri.tools;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
public enum OperatingSystem {
	WIN("Windows"),
	MAC("Mac OS X"),
	LINUX("Linux"),
	UNIX("Unix"),
	SOLARIS("Solaris"),
	OS2("OS2"),
	UNKOWN("Unkown");
	
	private final String label;
	
	private OperatingSystem(final String label) {
		this.label = label;
	}
	
	public String getLabel() {
		return this.label;
	}
}
