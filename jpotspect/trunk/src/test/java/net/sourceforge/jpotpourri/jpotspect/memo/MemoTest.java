package net.sourceforge.jpotpourri.jpotspect.memo;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
public class MemoTest {

	public static void main(final String[] args) {
		new MemoTest().genericTest();
	}

	@Test
	public final void genericTest() {
		final Calculator calc = new Calculator();

		assertEquals(7, calc.add(3, 4));
		assertEquals(6, calc.add(3, 3));
		assertEquals(7, calc.add(3, 4));

		assertEquals(2, calc.addExecuted);
	}


	/**
	 * @author christoph_pickl@users.sourceforge.net
	 */
	private static class Calculator {

		private int addExecuted;

		@Memorizable
		public int add(final int operand1, final int operand2) {
			this.addExecuted++;
			return operand1 + operand2;
		}

	}

}
