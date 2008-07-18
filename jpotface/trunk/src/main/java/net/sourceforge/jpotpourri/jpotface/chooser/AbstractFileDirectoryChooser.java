package net.sourceforge.jpotpourri.jpotface.chooser;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author christoph_pickl@users.sourceforge.net
 */
abstract class AbstractFileDirectoryChooser extends JPanel implements ActionListener {

	private static final long serialVersionUID = 6190683599071858829L;

	private static final Log LOG = LogFactory.getLog(AbstractFileDirectoryChooser.class);

    private static final int DIRECTORY_PATH_FIELD_SIZE = 15;
    
    static final String DEFAULT_BUTTON_LABEL = "Set";
    
    static final PtButtonPosition DEFAULT_BUTTON_POSITION = PtButtonPosition.RIGHT;
    
    
    private File fileOrDir;
    
    private JTextField directoryPath = new JTextField(DIRECTORY_PATH_FIELD_SIZE);

    /** Can be null. */
    private File defaultPath;

    private final String dialogTitle;
    
    private final JButton button;

    private final Set<IPtFileDirectoryChooserListener> listeners = new HashSet<IPtFileDirectoryChooserListener>();
    
    
    
    public AbstractFileDirectoryChooser(final String dialogTitle) {
        this(dialogTitle, null, DEFAULT_BUTTON_POSITION, DEFAULT_BUTTON_LABEL);
    }

    public AbstractFileDirectoryChooser(final String dialogTitle, final PtButtonPosition position) {
        this(dialogTitle, null, position, DEFAULT_BUTTON_LABEL);
    }

    public AbstractFileDirectoryChooser(final String dialogTitle, final File defaultPath,
    		final PtButtonPosition position) {
        this(dialogTitle, defaultPath, position, DEFAULT_BUTTON_LABEL);
    }
    
    public AbstractFileDirectoryChooser(final String dialogTitle, final File defaultPath,
    		final PtButtonPosition position, final String buttonLabel) {
        LOG.info("Constructing new directory chooser instance (buttonLabel=" + buttonLabel + "; "
        		+ "defaultPath=" + (defaultPath == null ? "null" : defaultPath.getAbsolutePath()) + ").");
        assert (buttonLabel.length() > 0);
        
        this.defaultPath = defaultPath;
        this.directoryPath.setEditable(false);
        this.dialogTitle = dialogTitle;
        
        this.button = new JButton(buttonLabel);
        this.button.setOpaque(false);
        this.button.addActionListener(this);
        
        this.initComponents(position);
    }
    
    
    
    private void initComponents(final PtButtonPosition btnPosition) {
        this.setOpaque(false);
        final GridBagLayout layout = new GridBagLayout();
        final GridBagConstraints c = new GridBagConstraints();
        layout.setConstraints(this, c);
        this.setLayout(layout);

        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.gridy = 0;
        c.weighty = 1.0;

        final int buttonTextGap = 6;

        c.fill = (btnPosition == PtButtonPosition.LEFT) ? GridBagConstraints.NONE : GridBagConstraints.HORIZONTAL;        
        c.insets = new Insets(0, 0, 0, (btnPosition == PtButtonPosition.LEFT) ? buttonTextGap : 0); // adjust right margin
        c.weightx = 0.0;
        c.gridx = 0;
        this.add((btnPosition == PtButtonPosition.LEFT) ? this.button : this.directoryPath, c);

        c.fill = (btnPosition == PtButtonPosition.LEFT) ? GridBagConstraints.HORIZONTAL : GridBagConstraints.NONE;
        c.insets = new Insets(0, (btnPosition == PtButtonPosition.LEFT) ? 0 : buttonTextGap, 0, 0); // adjust left margin
        c.weightx = 1.0;
        c.gridx++;
        this.add((btnPosition == PtButtonPosition.LEFT) ? this.directoryPath : this.button, c);
        
    }
    
    final void setFileOrDir(final File fileOrDir) {
        this.fileOrDir = fileOrDir;
        
        if (this.fileOrDir == null) {
            this.clearYourself();
        } else {
            if (this.fileOrDir.exists() && this.isRightFileOrDir(fileOrDir)) {
            	this.directoryPath.setText(this.fileOrDir.getAbsolutePath());
            } else {
            	final String errorMessage = this.fileOrDir.exists() == false
            			? "Given " + this.getFileDirName() + " '" + fileOrDir.getAbsolutePath() + "'  does not exist!"
            			: "The " + this.getFileDirName() + " '" + fileOrDir.getAbsolutePath() + "' is invalid!";
            	JOptionPane.showMessageDialog(this, errorMessage, "Invalid File", JOptionPane.WARNING_MESSAGE);
                this.clearYourself();
            }
        }
    }

    public final void uncheckedSetFileOrDir(final File directory) {
        this.fileOrDir = directory;
        this.directoryPath.setText(this.fileOrDir.getAbsolutePath());
    }
    
    // for future use: could clear folder-icon
    private void clearYourself() {
        this.directoryPath.setText("");
    }
    
    /**
     * @return can be null
     */
    final File getFileOrDir() {
        return this.fileOrDir;
    }
    
    abstract boolean isRightFileOrDir(final File file);
    
    /**
     * @return the string "File" or "Directory"
     */
    abstract String getFileDirName();
    
    public final void setDefaultPath(final File defaultPath) {
    	LOG.debug("Setting default path to '" + (defaultPath == null ? "null" : defaultPath.getAbsolutePath()) + "'.");
        this.defaultPath = defaultPath;
    }

    abstract int getSelectionMode();
    abstract FileFilter getFileFilter();
    
    public final void actionPerformed(final ActionEvent event) {
    	assert(event.getSource() == this.button);
        LOG.debug("showing file chooser with default path '"
        		+ (this.defaultPath == null ? "null" : this.defaultPath.getAbsolutePath()) + "'...");
        
        final JFileChooser chooser = new JFileChooser(this.defaultPath);
        chooser.setDialogTitle(this.dialogTitle);
        chooser.setMultiSelectionEnabled(false);
        chooser.setFileSelectionMode(getSelectionMode());
        
        if (getFileFilter() != null) {
            chooser.setFileFilter(getFileFilter());
        }
        
        int returnVal = chooser.showOpenDialog(AbstractFileDirectoryChooser.this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            final File selectedFileOrDir = chooser.getSelectedFile();
            assert (selectedFileOrDir != null);
            setFileOrDir(selectedFileOrDir);
            
            for (final IPtFileDirectoryChooserListener listener : this.listeners) {
                listener.doChoosen(selectedFileOrDir);
            }
        } else {
            LOG.debug("User canceled action.");
        }
    }
    
    
    public final void addChooserListener(final IPtFileDirectoryChooserListener listener) {
        this.listeners.add(listener);
    }
    public final void removeDirectoryChooserListener(final IPtFileDirectoryChooserListener listener) {
        this.listeners.remove(listener);
    }
    
    @Override
    public final void setEnabled(final boolean enabled) {
        this.button.setEnabled(enabled);
    }

    public final void setFixedButtonSize(final Dimension dimension) {
    	this.button.setMaximumSize(dimension);
    	this.button.setPreferredSize(dimension);
    	this.button.setSize(dimension);
    	this.button.setMinimumSize(dimension);
    }
    
}
