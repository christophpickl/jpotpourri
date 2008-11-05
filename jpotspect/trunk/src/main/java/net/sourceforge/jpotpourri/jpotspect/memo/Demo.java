package net.sourceforge.jpotpourri.jpotspect.memo;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
public class Demo {

	public static void main(String[] args) {
		new Demo().run();
	}
	
	private void run() {
		System.out.println("2 + 2 = " + this.add(2, 2));
		System.out.println("2 + 3 = " + this.add(2, 3));
		System.out.println("2 + 2 = " + this.add(2, 2)); // will use cached value
	}
	
	@Memorizable
	private int add(int operand1, int operand2) {
		System.out.println("computing add(" + operand1 + ", " + operand2 + ")");
		
		try {
			// simulate cpu processing
			Thread.sleep(1000 * 2);
		} catch (InterruptedException e) { /* ignored */ }
		
		return operand1 + operand2;
	}
	
}
