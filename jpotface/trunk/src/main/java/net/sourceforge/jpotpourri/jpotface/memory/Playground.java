package net.sourceforge.jpotpourri.jpotface.memory;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

/**
 * 
 * @author christoph_pickl@users.sourceforge.net
 */
class Playground {

	private static final PtAbstractMemoryStorage MEMORY = new PtPreferencesMemoryStorage();

	
	public static void main(final String[] args) {
		
		JTextField t = new JTextField(30);
		PtMemoryEnabler.enableMemoryOn(MEMORY, t, "default");
		
		JCheckBox c = new JCheckBox();
		PtMemoryEnabler.enableMemoryOn(MEMORY, c);
		
		NumberSpinner s = new NumberSpinner();
		PtMemoryEnabler.enableMemoryOn(MEMORY, s, "getNumber", "setNumber", int.class, true, 42);
		
		
		JButton b = new JButton("save");
		b.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				MEMORY.retain();
			}
		});
		
		JPanel p = new JPanel();
		p.add(t);
		p.add(c);
		p.add(b);
		p.add(s);
		
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		f.getContentPane().add(p);
		f.pack();
		f.setVisible(true);
	}
	
	private Playground() {
		// no instantiation
	}

	
	/**
	 * 
	 * @author christoph_pickl@users.sourceforge.net
	 */
	private static class NumberSpinner extends JTextField {
		
		private static final long serialVersionUID = 724452717233447193L;
		public NumberSpinner() {
			super("", 6);
		}
		
		public int getNumber() {
			return Integer.parseInt(this.getText());
		}
		public void setNumber(final int number) {
			this.setText("" + number);
		}
	}
}
