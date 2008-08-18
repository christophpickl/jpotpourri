package net.sourceforge.jpotpourri.jpotface.cairngorm;

import net.sourceforge.jpotpourri.jpotface.cairngorm.event.PtBindingEvent;

/**
 * interface for PtBxxx objects, which can receive an binding event to display its value(s)
 * 
 * @param <T> binding event type
 * 
 * @author christoph_pickl@users.sourceforge.net
 */
public interface IPtBindingListener<T> {

	void receiveEvent(final PtBindingEvent<T> event);
	
}
