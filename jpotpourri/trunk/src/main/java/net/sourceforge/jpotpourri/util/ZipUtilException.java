package net.sourceforge.jpotpourri.util;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
public class ZipUtilException extends Exception {

	private static final long serialVersionUID = 6958618862923528019L;

	public ZipUtilException(final String msg, final Exception cause) {
		super(msg, cause);
	}
	
	public ZipUtilException(final String msg) {
		super(msg);
	}
}
