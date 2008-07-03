package net.sourceforge.jpotpourri.gui.log4jlog;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import net.sourceforge.jpotpourri.gui.table.ITableBodyContextListener;
import net.sourceforge.jpotpourri.gui.table.TableBodyContext;

import org.apache.log4j.Logger;
import org.jdesktop.swingx.JXTable;

public class Dummy {

    /** class' own logger using log4j */
    private static final Logger LOG = Logger.getLogger(Dummy.class);

    private static class YTable extends JXTable implements ITableBodyContextListener {

    	public YTable(Object[][] vals, Object[] cols) {
    		super(vals, cols);
    		
    		final List<JMenuItem> itemsSingle = new ArrayList<JMenuItem>();
    		TableBodyContext.newJMenuItem(itemsSingle, "Show Exception Details", "CMD_EXCEPTION_DETAILS");
            final List<JMenuItem> itemsMultiple = new ArrayList<JMenuItem>();
            TableBodyContext.newJMenuItem(itemsMultiple, "Show Exception Details", "CMD_EXCEPTION_DETAILS");
            new TableBodyContext(this, itemsSingle, itemsMultiple, this);
    	}
    	
		public void contextMenuClicked(JMenuItem item, int tableRowSelected) {
			// TODO Auto-generated method stub
			
		}

		public void contextMenuClickedMultiple(JMenuItem item, int[] tableRowsSelected) {
			// TODO Auto-generated method stub
			
		}
    	
    }
    
	public static void main(String[] args) throws Exception {

		LOG.error("MAIN: INIT LOG ERROR"); // FIXME hack

		Object[][] vals = new Object[2][];
		vals[0] = new Object[] { "x1", "x2"};
		vals[1] = new Object[] { "y1", "y2"};
		Object[] cols = new Object[] { "C1", "C2" };
		YTable tbl = new YTable(vals, cols);
		
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		System.out.println("MAIN: getting JPOT appender!");
//		Log4jGuiHandler handler = Log4jGuiHandlerPool.getInstance().getLog4jGuiHandler("jpot");
		
		final JPanel panel = new JPanel(new BorderLayout());
		panel.add(new JTextField("asdf", 10), BorderLayout.NORTH);
//		panel.add(handler.getJComponent(), BorderLayout.CENTER);
		panel.add(new JScrollPane(tbl), BorderLayout.SOUTH);
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
