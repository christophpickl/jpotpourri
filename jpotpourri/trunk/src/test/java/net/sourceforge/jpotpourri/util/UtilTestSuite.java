package net.sourceforge.jpotpourri.util;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
public final class UtilTestSuite extends TestCase {
	
	public UtilTestSuite() {
		super("Util Test Suite");
	}

	public static Test suite() {
		final TestSuite suite = new TestSuite();
		
		suite.addTestSuite(CloseUtilTest.class);
		suite.addTestSuite(CollectionUtilTest.class);
		
		return suite;
	}

	public void testApp() {
		assertTrue(true);
	}
}
