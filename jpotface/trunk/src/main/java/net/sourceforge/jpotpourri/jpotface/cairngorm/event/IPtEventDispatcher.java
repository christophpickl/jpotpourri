package net.sourceforge.jpotpourri.jpotface.cairngorm.event;


/**
 * @author christoph_pickl@users.sourceforge.net
 */
public interface IPtEventDispatcher {

	void dispatch(final PtAbstractCairngormEvent<?> event);
	
	
	boolean addEventListener(final IPtEventListener listener);
	
	boolean removeEventListener(final IPtEventListener listener);
		
}
