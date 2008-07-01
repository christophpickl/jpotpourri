package net.sourceforge.jpotpourri.util;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
public class FileUtilException extends Exception {

	private static final long serialVersionUID = -2865381781057233654L;

	public FileUtilException(final String msg, final Exception cause) {
		super(msg, cause);
	}
	
	public FileUtilException(final String msg) {
		super(msg);
	}
}
