package net.sourceforge.jpotpourri.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import junit.framework.TestCase;

/**
 * @author christoph.pickl@bmi.gv.at
 */
public class CollectionUtilTest extends TestCase {

	public final void testAsCollection() {
		fail("Not yet implemented");
	}

	public final void testAsSet() {
		fail("Not yet implemented");
	}

	public final void testAsList() {
		fail("Not yet implemented");
	}

	public final void testAsImmutableList() {
		fail("Not yet implemented");
	}

	public final void testAsStringCollection() {
		fail("Not yet implemented");
	}

	public final void testAsStringSet() {
		fail("Not yet implemented");
	}

	public final void testAsArray() {
		fail("Not yet implemented");
	}

	public final void testToStringCollectionOfQ() {
		fail("Not yet implemented");
	}

	public final void testImmutableSetSetOfString() {
		fail("Not yet implemented");
	}

	public final void testImmutableSetStringArray() {
		fail("Not yet implemented");
	}

	public final void testImmutableList() {
		fail("Not yet implemented");
	}

	public final void testToStringSetOfObject() {

		assertEquals("null", CollectionUtil.toString((Set<?>) null));
		assertEquals("Set[]", CollectionUtil.toString(new HashSet<String>()));
		
		final Set<String> set = new LinkedHashSet<String>(2);
		set.add("abc");
		set.add("def");
		assertEquals("Set[abc;def]", CollectionUtil.toString(set));
	}

	public final void testToStringSetOfObjectString() {
		fail("Not yet implemented");
		// with own separator
	}


	public final void testToStringMapOfObjectObject() throws Exception {
		assertEquals("null", CollectionUtil.toString((Map<?, ?>) null));
		assertEquals("Map[]", CollectionUtil.toString(new HashMap<String, String>()));
		
		final Map<String, String> map = new LinkedHashMap<String, String>(4);
		map.put("a", "b");
		map.put("1", "2");
		map.put("x", "");
		map.put("y", null);
		assertEquals("Map[a=b;1=2;x=;y=null]", CollectionUtil.toString(map));
	}
}
