package net.sourceforge.jpotpourri.gui.log4jlog;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
public final class Err {
	
	private Err() {
		// no instantiation
	}
	
	public static void or(final String msg) {
		System.err.println("ERROR (jpotpourri.Log4jGuiAdapter): " + msg);
	}
}
