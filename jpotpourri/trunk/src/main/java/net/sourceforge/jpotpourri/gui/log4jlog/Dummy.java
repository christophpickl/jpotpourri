package net.sourceforge.jpotpourri.gui.log4jlog;

import javax.swing.JFrame;

import org.apache.log4j.Logger;

public class Dummy {

    /** class' own logger using log4j */
    private static final Logger LOG = Logger.getLogger(Dummy.class);

	public static void main(String[] args) throws Exception {

		LOG.error("MAIN: INIT LOG ERROR"); // FIXME hack
		
		JFrame f = new JFrame();
		System.out.println("MAIN: getting JPOT appender!");
		Log4jGuiHandler handler = Log4jGuiHandlerPool.getInstance().getLog4jGuiHandler("jpot");
		f.getContentPane().add(handler.getJComponent());
		f.pack();
		f.setVisible(true);

		System.out.println("MAIN: going to use jpot log adapter.");
		LOG.trace("jpot trace");
		Thread.sleep(100);
		LOG.debug("jpot debug");
		Thread.sleep(100);
		LOG.info("jpot info");
		Thread.sleep(100);
		LOG.warn("jpot warn 1/3");
//		Thread.sleep(100);
//		LOG.warn("jpot warn 2/3");
//		Thread.sleep(100);
//		LOG.warn("jpot warn 3/3");
		Thread.sleep(100);
		LOG.error("jpot error 1/3");
//		Thread.sleep(100);
//		LOG.error("jpot error 2/3");
//		Thread.sleep(100);
//		LOG.error("jpot error 3/3");
		Thread.sleep(100);
		LOG.fatal("jpot fatal");
		System.out.println("MAIN: finished.");
	}
}
