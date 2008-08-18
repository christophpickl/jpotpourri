package net.sourceforge.jpotpourri.jpotface.cairngorm.component.fireonly;

import net.sourceforge.jpotpourri.jpotface.cairngorm.event.PtAbstractCairngormEvent;
import net.sourceforge.jpotpourri.jpotface.cairngorm.event.PtEventDispatcher;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
abstract class AbstractEventComponentSkeleton implements IPtBindableFireComponent {

	public final void dispatchEvent(final PtAbstractCairngormEvent<?> event) {
		PtEventDispatcher.getInstance().dispatch(event);
	}

}
