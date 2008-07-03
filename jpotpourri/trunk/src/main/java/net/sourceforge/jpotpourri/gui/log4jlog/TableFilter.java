package net.sourceforge.jpotpourri.gui.log4jlog;

import net.sourceforge.jpotpourri.fprog.predsfuncs.IUnaryPredicate;

import org.apache.log4j.Level;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
public class TableFilter implements IUnaryPredicate<Log4jEvent> {
	
	private final Level level;
	private final String searchString;
	
	public static final TableFilter FILTER_NONE = new TableFilter(Level.ALL, null);
	
	/**
	 * @param level log4j loglevel
	 * @param searchString can be null
	 */
	public TableFilter(final Level level, final String searchString) {
		this.level = level;
		this.searchString = searchString;
	}
	
	public static TableFilter newBySearchString(final TableFilter thatFilter, final String searchString) {
		if(thatFilter == null) {
			throw new NullPointerException("thatFilter");
		}
		return new TableFilter(thatFilter.level, searchString);
	}
	
	// increase performance
	public final boolean isFilterOff() {
		return this.level == Level.ALL && this.searchString == null;
	}
	
	// none will be come through filter (Level == Level.OFF)
	public final boolean isFilterAll() {
		return this.level == Level.OFF;
	}
	
	public final boolean execute(final Log4jEvent event) {
		boolean levelOkay = event.getLevel().isGreaterOrEqual(this.level);
		
		// TODO maybe split searchString by " ", to enable multiple search terms
		if(levelOkay == true && this.searchString != null) {
			levelOkay =
				event.getMessageRendered().contains(this.searchString) ||
				event.getLogClassName().contains(this.searchString) ||
				event.getLogMethod().contains(this.searchString) ||
				event.getThreadName().contains(this.searchString);
		}
		
		return levelOkay;
	}
	
}
