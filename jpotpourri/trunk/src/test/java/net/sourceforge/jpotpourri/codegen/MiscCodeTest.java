package net.sourceforge.jpotpourri.codegen;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

/**
 * @author christoph.pickl@bmi.gv.at
 */
public final class MiscCodeTest extends TestCase {

	public void testGenArgument() throws Exception {
		final List<GenArgument> expected = new ArrayList<GenArgument>(2);
		expected.add(new GenArgument("String", "foo"));
		expected.add(new GenArgument("int", "i"));
		
		final List<GenArgument> actual = GenArgument.newList("String", "foo", "int", "i");
		
		assertEquals(expected.size(), actual.size());
		for (int i = 0; i < expected.size(); i++) {
			assertEquals(expected.get(i), actual.get(i));
		}
	}
}
