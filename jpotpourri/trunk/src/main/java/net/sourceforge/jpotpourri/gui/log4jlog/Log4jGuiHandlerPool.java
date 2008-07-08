package net.sourceforge.jpotpourri.gui.log4jlog;

import java.awt.BorderLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
public final class Log4jGuiHandlerPool { // stays public
	

//    private static final Logger LOG = Logger.getLogger(Log4jGuiHandlerPool.class);
    
	private static final Log4jGuiHandlerPool INSTANCE = new Log4jGuiHandlerPool();
	
//	private static boolean isStartedUp; // FIXME hack, a want-to-be workaround :(
//	static {
//		if(isStartedUp == false) {
//			isStartedUp = true;
//			System.out.println("GuiHandlerPool isssss starting up");
//			LOG.info(Log4jGuiAdapter.class.getName() + " is starting up...");
//		}
//	}
	
	
	private Log4jGuiHandlerPool() {
		// singleton
	}
	
	public static Log4jGuiHandlerPool getInstance() {
		return INSTANCE;
	}
	
	private final Map<String, Log4jGuiHandler> instances = new HashMap<String, Log4jGuiHandler>();
	
	boolean isLog4jGuiHandlerRegistered(final String appenderName) {
		final boolean result = this.instances.containsKey(appenderName);
		if(result == false) {
			De.bug("Log4jGuiHandlerPool --- GuiHandler NOT registered named [" + appenderName + "] " +
					"(size=" + this.instances.size() + ").");
		}
		return result;
	}
	
	Log4jGuiHandler getLog4jGuiHandlerInternal(final String appenderName) {
		De.bug("Log4jGuiHandlerPool --- Getting handler by appender name [" + appenderName + "] " +
				"(size=" + this.instances.size() + ")");
		assert(this.isLog4jGuiHandlerRegistered(appenderName));
		return this.instances.get(appenderName);
	}
	Log4jGuiHandler createLog4jGuiHandler(final Log4jGuiHandlerDefinition handlerDefinition) {
		final String appenderName = handlerDefinition.getAppenderName();
		assert (this.isLog4jGuiHandlerRegistered(appenderName) == false);

		
		if(handlerDefinition.isSystemLafEnabled()) {
			try {
	        	De.bug("setting system laf.");
	            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	        } catch (Exception ex) {
	            Err.or("Could not set system laf: " + ex.getMessage());
	        }
		}
		
		
		final Log4jGuiHandler result = new Log4jGuiHandler(handlerDefinition);
		De.bug("Log4jGuiHandlerPool --- inserting gui handler for appender [" + appenderName + "]: " + result + " " +
				"(size=" + this.instances.size() + ";thread=" + Thread.currentThread().getName() + ")");
		this.instances.put(appenderName, result);
		De.bug("Log4jGuiHandlerPool --- pool size after insert: " + this.instances.size() + "");

		
		final String debugProperty = System.getProperty(JPotGuiAppender.SYSPROPERT_SHOW_DEBUG_GUI);
		final boolean debugEnabled = debugProperty != null && debugProperty.length() > 0;
		if(debugEnabled) {
			final DebugFrame debugFrame = new DebugFrame(result);
			result.setDebugFrame(debugFrame);
			debugFrame.setVisible(true);
		}
		
		return result;
	}

	/**
	 * @author christoph_pickl@users.sourceforge.net
	 */
	private static class DebugFrame extends JFrame {
		private static final long serialVersionUID = 4800350316457263714L;

		public DebugFrame(final Log4jGuiHandler handler) {
			this.setTitle("JPotGuiAppender - " + handler.getAppenderName());
			this.setResizable(true);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			final JPanel panel = new JPanel(new BorderLayout());
			panel.add(handler.getJComponent(), BorderLayout.CENTER);
			
			this.getContentPane().add(panel);
//			final Dimension dim = new Dimension(400, 200);
//			this.setMinimumSize(dim);
//			this.setSize(dim);
//			this.setPreferredSize(dim);
//			this.setMaximumSize(dim);
			this.pack();
			this.setLocationRelativeTo(null);
		}
	}
	
	
	
	public Log4jGuiHandler getLog4jGuiHandler(final String appenderName) {
		final Log4jGuiHandler result = this.instances.get(appenderName);
		if(result == null) {
			throw new IllegalArgumentException("Unkown Log4jGuiAdapter name [" + appenderName + "]!");
		}
		return result;
	}
}
