package net.sourceforge.jpotpourri.jpotspect.notnull;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
public class Demo {

	public static void main(String[] args) {
		new Demo().run();
	}
	
	private void run() {
		this.setSomeNull("not null");
		this.setSomeNull(null); // NullPointerException will be thrown
	}
	
	public void setSomeNull(@NotNull String value) {
		System.out.println("setting value to [" + value + "]");
	}
	
}
