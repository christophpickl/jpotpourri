package net.sourceforge.jpotpourri.codegen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
public final class GenArgument implements IJavaCode {
	
	private final String type;

	private final String name;
	
	GenArgument(final String type, final String name) {
		if(type == null) {
			throw new NullPointerException("type");
		}
		if(name == null) {
			throw new NullPointerException("name");
		}
		this.type = type;
		this.name = name;
	}
	
	/**
	 * @param args eg: ("String", "arg1", "int", "arg")
	 */
	public static List<GenArgument> newList(final String... args) {
		if(args.length % 2 == 1) {
			throw new IllegalArgumentException("Argument length must be even!");
		}
		final List<GenArgument> list = new ArrayList<GenArgument>();
		
		for (int i = 0, n = args.length / 2; i < n; i++) {
			list.add(new GenArgument(args[i], args[i + 1]));
		}
		
		return Collections.unmodifiableList(list);
	}

	@Override
	public boolean equals(final Object other) {
		if((other instanceof GenArgument) == false) {
			return false;
		}
		final GenArgument that = (GenArgument) other;
		return this.type.equals(that.type) && this.name.equals(that.name);
	}

	@Override
	public int hashCode() {
		return this.type.hashCode() + this.name.hashCode() + 17;
	}

	public String toCode() {
		return "final " + this.type + " " + this.name;
	}
	

	public String getName() {
		return this.name;
	}
}
