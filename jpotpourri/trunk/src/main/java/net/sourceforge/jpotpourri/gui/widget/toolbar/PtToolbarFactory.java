package net.sourceforge.jpotpourri.gui.widget.toolbar;

import java.util.Arrays;
import java.util.List;

import javax.swing.JPanel;

public final class PtToolbarFactory {


	public static IPtToolbar newPtToolbar(final JPanel contentPanel, final List<IPtToolbarItem> items) {
		return new Toolbar(contentPanel, items);
	}

	public static IPtToolbar newPtToolbar(final JPanel contentPanel, final IPtToolbarItem... items) {
		return newPtToolbar(contentPanel, Arrays.asList(items));
	}
}
