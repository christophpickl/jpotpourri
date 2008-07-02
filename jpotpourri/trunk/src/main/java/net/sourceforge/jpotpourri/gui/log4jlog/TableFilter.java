package net.sourceforge.jpotpourri.gui.log4jlog;

import net.sourceforge.jpotpourri.fprog.predsfuncs.IUnaryPredicate;

import org.apache.log4j.Level;

public class TableFilter implements IUnaryPredicate<Log4jEvent> {
	
	private final Level level;
	private final String searchString;
	
	public static final TableFilter FILTER_NONE = new TableFilter(Level.ALL, null);
	
	/**
	 * @param level log4j loglevel
	 * @param searchString can be null
	 */
	public TableFilter(Level level, String searchString) {
		this.level = level;
		this.searchString = searchString;
	}
	
	// increase performance
	public boolean isFilterOff() {
		return this.level == Level.ALL && this.searchString == null;
	}
	
	public boolean execute(final Log4jEvent event) {
		boolean levelOkay = event.getLevel().isGreaterOrEqual(this.level);
		
		if(levelOkay == true && this.searchString != null) {
			levelOkay = event.getMessage().toString().contains(this.searchString);
		}
		
		return levelOkay;
	}
	
}
