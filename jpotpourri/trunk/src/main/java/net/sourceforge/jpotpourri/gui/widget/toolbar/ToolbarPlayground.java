package net.sourceforge.jpotpourri.gui.widget.toolbar;

import java.awt.Toolkit;
import java.util.LinkedList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class ToolbarPlayground {



	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				initGui();
			}
		});
	}
	
	private static void initGui() {
		final JFrame f = new JFrame();
		f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		final JPanel p = new JPanel();
		p.add(new JLabel("main"));
		
		final List<IPtToolbarItem> items = new LinkedList<IPtToolbarItem>();
		items.add(new PtDefaultToolbarItem("LBL1", loadImageIcon("/delme_toolbar1.png"), "CMD_ITEM1"));
		items.add(new PtDefaultToolbarItem("LBL2", loadImageIcon("/delme_toolbar2.png"), "CMD_ITEM2"));
		final IPtToolbar tb = PtToolbarFactory.newPtToolbar(p, items);
		
		tb.addIPtToolbarListener(new IPtToolbarListener() {
			public void doToolbarClicked(IPtToolbarItem item) {
				System.out.println("toolbar clicked: " + item);
			}
		});
		
		f.getContentPane().add(tb.asJComponent());
		f.pack();
		f.setVisible(true);
	}
	
	private static ImageIcon loadImageIcon(String imagePath) {
		System.out.println(imagePath);
		return new ImageIcon(Toolkit.getDefaultToolkit().getImage(ToolbarPlayground.class.getResource(imagePath)));
	}
}
