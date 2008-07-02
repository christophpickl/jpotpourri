package net.sourceforge.jpotpourri.gui.log4jlog;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.log4j.Level;

// stays public!
public final class AllLogLevels {

	private AllLogLevels() {
		// no instantiation
	}
	
	public static final Set<Level> LEVELS;
	static {
		 final Set<Level> tmp = new LinkedHashSet<Level>();
		 tmp.add(Level.OFF);
		 tmp.add(Level.TRACE);
		 tmp.add(Level.DEBUG);
		 tmp.add(Level.TRACE);
		 tmp.add(Level.INFO);
		 tmp.add(Level.WARN);
		 tmp.add(Level.ERROR);
		 tmp.add(Level.FATAL);
		 tmp.add(Level.ALL);
		 LEVELS = Collections.unmodifiableSet(tmp);
	}
}
