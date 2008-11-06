package net.sourceforge.jpotpourri.jpotspect.notnull;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
class Demo {

	public static void main(final String[] args) {
		new Demo().run();
	}

	private Demo() {
		// nothing to do
	}
	
	private void run() {
		this.setSomeNull("not null");
		this.setSomeNull(null); // NullPointerException will be thrown
	}
	
	public void setSomeNull(@NotNull final String value) {
		System.out.println("setting value to [" + value + "]");
	}
	
}
