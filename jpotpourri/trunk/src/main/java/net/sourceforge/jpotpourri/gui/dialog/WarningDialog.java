package net.sourceforge.jpotpourri.gui.dialog;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import net.sourceforge.jpotpourri.gui.EscapeDisposer;
import net.sourceforge.jpotpourri.gui.IEscapeDisposeReceiver;
import net.sourceforge.jpotpourri.gui.JLibImageFactory;
import net.sourceforge.jpotpourri.util.GuiUtil;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
public final class WarningDialog extends JDialog {
	// TODO merge with ErrorDialog
	
	private static final long serialVersionUID = 3177678362788742617L;

	private final JButton btnClose = new JButton("Close");

	
	

	public static WarningDialog newWarningDialog(final String title, final String message) {
		final JPanel contentPanel = new JPanel();
		contentPanel.add(new JLabel(message));
		return new WarningDialog(title, contentPanel, null);
	}
	
	public static WarningDialog newWarningDialog(final String title, final JPanel contentPanel) {
		return new WarningDialog(title, contentPanel, null);
	}
	
	
	public static WarningDialog newWarningDialog(final JFrame owner, final String title, final String message) {
		final JPanel contentPanel = new JPanel();
		contentPanel.add(new JLabel(message));
		return newWarningDialog(owner, title, contentPanel, null);
	}
	
	public static WarningDialog newWarningDialog(final JFrame owner, final String title, final JPanel contentPanel) {
		return new WarningDialog(owner, title, contentPanel, null);
	}
	
	
	public static WarningDialog newWarningDialog(final JDialog owner, final String title, final String message) {
		final JPanel contentPanel = new JPanel();
		contentPanel.add(new JLabel(message));
		return newWarningDialog(owner, title, contentPanel, null);
	}
	
	public static WarningDialog newWarningDialog(final JDialog owner, final String title, final JPanel contentPanel) {
		return new WarningDialog(owner, title, contentPanel, null);
	}
	
	

	
	public static WarningDialog newWarningDialog(final String title, final String message, 
			final Color backgroundColor) {
		final JPanel contentPanel = new JPanel();
		contentPanel.add(new JLabel(message));
		return new WarningDialog(title, contentPanel, backgroundColor);
	}
	
	public static WarningDialog newWarningDialog(final String title, final JPanel contentPanel,
			final Color backgroundColor) {
		return new WarningDialog(title, contentPanel, backgroundColor);
	}
	
	
	public static WarningDialog newWarningDialog(final JFrame owner, final String title, final String message,
			final Color backgroundColor) {
		final JPanel contentPanel = new JPanel();
		contentPanel.add(new JLabel(message));
		return newWarningDialog(owner, title, contentPanel, backgroundColor);
	}
	
	public static WarningDialog newWarningDialog(final JFrame owner, final String title, final JPanel contentPanel,
			final Color backgroundColor) {
		return new WarningDialog(owner, title, contentPanel, backgroundColor);
	}
	
	
	public static WarningDialog newWarningDialog(final JDialog owner, final String title, final String message,
			final Color backgroundColor) {
		final JPanel contentPanel = new JPanel();
		contentPanel.add(new JLabel(message));
		return newWarningDialog(owner, title, contentPanel, backgroundColor);
	}
	
	public static WarningDialog newWarningDialog(final JDialog owner, final String title, final JPanel contentPanel,
			final Color backgroundColor) {
		return new WarningDialog(owner, title, contentPanel, backgroundColor);
	}
	

	
	private WarningDialog(final String title, final JPanel contentPanel, final Color backgroundColor) {
		this.pseudoConstructor(title, contentPanel, backgroundColor);
	}
	
	private WarningDialog(final JFrame owner, final String title, final JPanel contentPanel,
			final Color backgroundColor) {
		super(owner);
		this.pseudoConstructor(title, contentPanel, backgroundColor);
	}
	
	private WarningDialog(final JDialog owner, final String title, final JPanel contentPanel,
			final Color backgroundColor) {
		super(owner);
		this.pseudoConstructor(title, contentPanel, backgroundColor);
	}

	public void setButtonLabel(final String buttonLabel) {
		this.btnClose.setText(buttonLabel);
	}
	
	private void pseudoConstructor(final String title, final JPanel contentPanel, final Color backgroundColor) {
		this.setTitle(title);
		this.setModal(true);

		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(final WindowEvent event) {
				doClose();
			}
		});

		EscapeDisposer.enableEscape(this.getRootPane(), new IEscapeDisposeReceiver() {
			public void doEscape() {
				doClose();
			}
		});
		

		final JPanel panel = new JPanel(new BorderLayout());
		panel.setBackground(backgroundColor);
		panel.setBorder(BorderFactory.createEmptyBorder(10, 16, 10, 16)); // top left bottom right
		panel.add(this.initComponents(contentPanel), BorderLayout.CENTER);
		
		this.getContentPane().add(panel);
		
		this.pack();
		this.setResizable(false);
		GuiUtil.setCenterLocation(this);
	}
	
	private JPanel initComponents(final JPanel contentPanel) {
		final JPanel wrapPanel = new JPanel(new BorderLayout());
		

		JPanel westPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		westPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
		westPanel.add(new JLabel(JLibImageFactory.getInstance().getDialogWarning()));

		final JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		centerPanel.add(contentPanel);
		

		JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		southPanel.add(this.btnClose);
		this.btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent event) {
			doClose();
		} });
		this.getRootPane().setDefaultButton(this.btnClose);

		contentPanel.setOpaque(false);
		westPanel.setOpaque(false);
		southPanel.setOpaque(false);
		centerPanel.setOpaque(false);
		wrapPanel.setOpaque(false);

		wrapPanel.add(westPanel, BorderLayout.WEST);
		wrapPanel.add(southPanel, BorderLayout.SOUTH);
		wrapPanel.add(centerPanel, BorderLayout.CENTER);
		return wrapPanel;

	}
	
	

	
	private void doClose() {
		this.dispose();
	}
	
	
	
	
	public static void main(final String[] args) {
		newWarningDialog("Warn dial", "das ist meine message").setVisible(true);
	}
}
