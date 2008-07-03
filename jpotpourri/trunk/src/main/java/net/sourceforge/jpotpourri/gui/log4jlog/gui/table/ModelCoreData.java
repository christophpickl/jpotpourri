package net.sourceforge.jpotpourri.gui.log4jlog.gui.table;

import java.util.LinkedList;
import java.util.List;

import net.sourceforge.jpotpourri.fprog.Fp;
import net.sourceforge.jpotpourri.gui.log4jlog.De;
import net.sourceforge.jpotpourri.gui.log4jlog.Log4jEvent;
import net.sourceforge.jpotpourri.gui.log4jlog.TableFilter;

class ModelCoreData {

	private List<Log4jEvent> allEvents = new LinkedList<Log4jEvent>();
	private List<Log4jEvent> filteredEvents = new LinkedList<Log4jEvent>();
	
	
//	private final Map<Level, List<LoggingEvent>> map = new HashMap<Level, List<LoggingEvent>>();
	
	private TableFilter filter;
	
	public ModelCoreData() {
//		for (Level level : LOG_LEVELS) {
//			this.map.put(level, new LinkedList<LoggingEvent>());
//		}
	}
	
	
	public synchronized void addLoggingEvent(final Log4jEvent event) {
		this.allEvents.add(event);
		
		if(this.isFilter() && this.filter.execute(event)) {
			this.filteredEvents.add(event);
		}
	}
	
	public synchronized Log4jEvent get(int index) {
		return this.list().get(index);
	}
	
	public synchronized int getSize() {
		return this.list().size();
	}
	
	private List<Log4jEvent> list() {
		return this.isFilter() ? this.filteredEvents : this.allEvents;
	}

	public synchronized void doFilter(final TableFilter newFilter) {
		if(newFilter == null) {
			throw new NullPointerException("filter");
		}
		if(newFilter.isFilterOff()) { // filter.level == Level.ALL && searchString == null
			this.doUnfilter();
		} else {
			De.bug("ModelCoreData --- filtering with filter: " + newFilter);
			this.filter = newFilter;
			final List<Log4jEvent> filteredList;
			if(this.filter.isFilterAll()) { // filter.level == Level.OFF
				filteredList = new LinkedList<Log4jEvent>();
			} else {
				filteredList = new LinkedList<Log4jEvent>(Fp.<Log4jEvent>filter(this.filter, this.allEvents));
			}
			this.filteredEvents = filteredList;
		}
	}
	
	public synchronized void doUnfilter() {
		De.bug("ModelCoreData --- doUnfilter() invoked");
		this.filter = null;
	}
	private boolean isFilter() {
		return this.filter != null;
	}
}
