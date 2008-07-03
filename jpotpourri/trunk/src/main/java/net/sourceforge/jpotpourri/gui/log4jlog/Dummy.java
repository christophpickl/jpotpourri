package net.sourceforge.jpotpourri.gui.log4jlog;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import org.apache.log4j.Logger;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
public final class Dummy {

    /** class' own logger using log4j */
    private static final Logger LOG = Logger.getLogger(Dummy.class);

    private Dummy() {
    	// nothing to do
    }
    
	public static void main(final String[] args) throws Exception {

		LOG.error("MAIN: INIT LOG ERROR"); // FIXME hack

		
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		System.out.println("MAIN: getting JPOT appender!");
//		Log4jGuiHandler handler = Log4jGuiHandlerPool.getInstance().getLog4jGuiHandler("jpot");
		
		final JPanel panel = new JPanel(new BorderLayout());
		panel.add(new JTextField("asdf", 10), BorderLayout.NORTH);
//		panel.add(handler.getJComponent(), BorderLayout.CENTER);
		f.getContentPane().add(panel);
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
		Thread.sleep(100);
		LOG.fatal("throwing an exception", new Exception("tut"));
		System.out.println("MAIN: finished.");
	}
}
