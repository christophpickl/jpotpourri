package net.sourceforge.jpotpourri.gui;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JTable;

import net.sourceforge.jpotpourri.util.GuiUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * @author christoph_pickl@users.sourceforge.net
 */
public final class EscapeDisposer {
	
	private static final Log LOG = LogFactory.getLog(EscapeDisposer.class);
	
	
	private EscapeDisposer() {
		// no instantiation
	}
	
	public static void enableEscapeOnDialogWithoutFocusableComponents(final JDialog dialog,
			final IEscapeDisposeReceiver receiver) {
		LOG.debug("Enabling dialog-without-focusable-components escape notification on receiver with class " +
				"'" + receiver.getClass().getName() + "'.");
    	
		dialog.addKeyListener(new KeyAdapter() {
    		@Override
			public void keyPressed(final KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					LOG.debug("Broadcasting dialog-without-focusable-components escape event to listener with class " +
							"'" + receiver.getClass().getName() + "'.");
					receiver.doEscape();
				}
			}
    	});
	}
    
    public static void enableEscape(final JTable table, final IEscapeDisposeReceiver receiver) {
    	LOG.debug("Enabling table escape notification on receiver with class '" + receiver.getClass().getName() + "'.");
    	
    	table.addKeyListener(new KeyAdapter() {
    		@Override
			public void keyPressed(final KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					LOG.debug("Broadcasting table escape event to listener with class " +
							"'" + receiver.getClass().getName() + "'.");
					receiver.doEscape();
				}
			}
    	});
    	
    }
	//  JComponent JRootPane
    public static void enableEscape(final JComponent rootPane, final IEscapeDisposeReceiver receiver) {
    	LOG.debug("Enabling rootpane escape notification on receiver with class " +
    			"'" + receiver.getClass().getName() + "'.");
    	
		GuiUtil.addGlobalKeyListener(rootPane, new IGlobalKeyListener() {
			public void doKeyPressed(final GlobalKey key) {
				if(key == GlobalKey.ESCAPE) {
					LOG.debug("Broadcasting rootpane escape event to listener with class " +
							"'" + receiver.getClass().getName() + "'.");
					receiver.doEscape();
				}
			}
		});
    }
    
}

