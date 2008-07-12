package net.sourceforge.jpotpourri.fprog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import junit.framework.TestCase;
import net.sourceforge.jpotpourri.fprog.predsfuncs.IBinaryFunction;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
public class FpTest extends TestCase {

//	public final void testMap() {
//		fail("Not yet implemented");
//	}
//
//	public final void testFilter() {
//		fail("Not yet implemented");
//	}

	@SuppressWarnings("boxing")
	public final void testReduce() {
		List<Integer> list = new ArrayList<Integer>();
		list.add(3);
		list.add(6);
		list.add(2);
		list = Collections.unmodifiableList(list);
		
		final IBinaryFunction<Integer, Integer> reduceNums = new IBinaryFunction<Integer, Integer>() {
			public Integer execute(final Integer t1, final Integer t2) {
				return t1.intValue() + t2.intValue();
			}
		};
		
		assertEquals(11,  Fp.reduce(reduceNums, list, 0).intValue());
		assertEquals(21, Fp.reduce(reduceNums, list, 10).intValue());

	}

}
