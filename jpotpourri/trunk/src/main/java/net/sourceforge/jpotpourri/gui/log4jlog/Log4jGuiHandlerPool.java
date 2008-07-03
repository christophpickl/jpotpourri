package net.sourceforge.jpotpourri.gui.log4jlog;

import java.util.HashMap;
import java.util.Map;

final class Log4jGuiHandlerPool {

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
		if(result == false) De.bug("Log4jGuiHandlerPool --- GuiHandler NOT registered named ["+appenderName+"] (size="+this.instances.size()+").");
		return result;
	}
	
	Log4jGuiHandler getLog4jGuiHandlerInternal(final String appenderName) {
		De.bug("Log4jGuiHandlerPool --- Getting handler by appender name ["+appenderName+"] (size="+this.instances.size()+")");
		assert(this.isLog4jGuiHandlerRegistered(appenderName));
		return this.instances.get(appenderName);
	}
	Log4jGuiHandler createLog4jGuiHandler(final Log4jGuiHandlerDefinition handlerDefinition) {
		final String appenderName = handlerDefinition.getAppenderName();
		assert(this.isLog4jGuiHandlerRegistered(appenderName) == false);
		
		final Log4jGuiHandler result = new Log4jGuiHandler(handlerDefinition);
		De.bug("Log4jGuiHandlerPool --- inserting gui handler for appender ["+appenderName+"]: " + result + " (size="+this.instances.size()+")");
		this.instances.put(appenderName, result);
		
		De.bug("Log4jGuiHandlerPool --- after inserting is size="+this.instances.size()+"");

		return result;
	}

	
	
	
	public Log4jGuiHandler getLog4jGuiHandler(final String appenderName) {
		final Log4jGuiHandler result = this.instances.get(appenderName);
		if(result == null) {
			throw new IllegalArgumentException("Unkown Log4jGuiAdapter name [" + appenderName + "]!");
		}
		return result;
	}
}
