package net.sourceforge.jpotpourri.codegen;

public final class ManClassDefinition {
	
	private String packageName = null;
	
	
	public ManClassDefinition() {
		// nothing to do
	}
	
	
	public void setPackageName(final String packageName) {
		this.packageName = packageName;
	}
	
	public String getPackageName() {
		return this.packageName;
	}
}
