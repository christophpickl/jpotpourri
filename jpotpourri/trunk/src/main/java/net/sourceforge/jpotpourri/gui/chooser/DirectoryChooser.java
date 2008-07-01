package net.sourceforge.jpotpourri.gui.chooser;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

/**
 * 
 * @author christoph_pickl@users.sourceforge.net
 */
public class DirectoryChooser extends AbstractFileDirectoryChooser {

//    private static final Log LOG = LogFactory.getLog(DirectoryChooser.class);
    
    private static final long serialVersionUID = -3831195688364368872L;
    
    

    public DirectoryChooser(final String dialogTitle) {
        super(dialogTitle);
    }
    
    public DirectoryChooser(final String dialogTitle, final ButtonPosition position) {
        super(dialogTitle, position);
    }
    
    public DirectoryChooser(final String dialogTitle, final File defaultPath, final ButtonPosition position) {
        super(dialogTitle, defaultPath, position);
    }
    
    public DirectoryChooser(final String dialogTitle, final File defaultPath, final ButtonPosition position,
    		final String buttonLabel) {
        super(dialogTitle, defaultPath, position, buttonLabel);
    }


    /**
     * @return can be null
     */
    public final File getSelectedDirectory() {
        return this.getFileOrDir();
    }
    
    @Override
    final int getSelectionMode() {
        return JFileChooser.DIRECTORIES_ONLY;
    }

    @Override
    final FileFilter getFileFilter() {
        return null;
    }

    @Override
    final boolean isRightFileOrDir(final File file) {
        return file.isDirectory();
    }
    
    public final void setDirectory(final File directory) {
        this.setFileOrDir(directory);
    }

	@Override
	final String getFileDirName() {
		return "Directory";
	}
}
