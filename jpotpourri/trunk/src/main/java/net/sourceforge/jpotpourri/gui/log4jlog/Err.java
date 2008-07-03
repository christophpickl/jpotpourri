package net.sourceforge.jpotpourri.gui.log4jlog;

public final class Err {
	
	private Err() {
		// no instantiation
	}
	
	public static void or(String msg) {
		System.err.println("ERROR (jpotpourri.Log4jGuiAdapter): " + msg);
	}
}
