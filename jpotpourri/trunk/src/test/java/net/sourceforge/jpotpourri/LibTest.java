package net.sourceforge.jpotpourri;

import net.sourceforge.jpotpourri.util.UtilTestSuite;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
public final class LibTest extends TestCase {
	
	public LibTest() {
		super("Global Test Suite");
	}

	public static Test suite() {
		final TestSuite suite = new TestSuite();
		
		suite.addTestSuite(UtilTestSuite.class);
		
		return suite;
	}

	public void testApp() {
		assertTrue(true);
	}
}
