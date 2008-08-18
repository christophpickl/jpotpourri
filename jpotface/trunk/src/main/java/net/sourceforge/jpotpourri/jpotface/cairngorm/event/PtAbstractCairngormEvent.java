package net.sourceforge.jpotpourri.jpotface.cairngorm.event;


/**
 *
 * @param <T> type of event key
 * 
 * @author christoph_pickl@users.sourceforge.net
 */
public abstract class PtAbstractCairngormEvent<T> {

	private final IPtEventType<T> type;
	
	private final Object source;

	
	public PtAbstractCairngormEvent(final IPtEventType<T> type, final Object source) {
		this.type = type;
		this.source = source;
	}


	public IPtEventType<T> getType() {
		return this.type;
	}
	
	public Object getSource() {
		return this.source;
	}
	
}
