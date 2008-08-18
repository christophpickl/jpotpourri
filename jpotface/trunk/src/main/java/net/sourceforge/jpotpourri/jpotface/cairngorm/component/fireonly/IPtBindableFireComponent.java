package net.sourceforge.jpotpourri.jpotface.cairngorm.component.fireonly;

import net.sourceforge.jpotpourri.jpotface.cairngorm.event.PtAbstractCairngormEvent;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
public interface IPtBindableFireComponent {

	void dispatchEvent(final PtAbstractCairngormEvent<?> event);
	
}
