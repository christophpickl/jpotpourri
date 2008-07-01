package net.sourceforge.jpotpourri.gui.chooser;


import java.io.File;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import net.sourceforge.jpotpourri.util.CollectionUtil;
import net.sourceforge.jpotpourri.util.FileUtil;


/**
 * 
 * @author christoph_pickl@users.sourceforge.net
 */
public class FileChooser extends AbstractFileDirectoryChooser {

//    private static final Log LOG = LogFactory.getLog(DirectoryChooser.class);
    
    private static final long serialVersionUID = 4870536517103469362L;

    private final FileFilter fileFilter;
    
    
    /** simplest constructor. */
    public FileChooser(final String dialogTitle, final String... validExtensions) {
        this(dialogTitle, AbstractFileDirectoryChooser.DEFAULT_BUTTON_LABEL, null,
        		AbstractFileDirectoryChooser.DEFAULT_BUTTON_POSITION, validExtensions);
    }
    
    /** adds button position. */
    public FileChooser(final String dialogTitle, final ButtonPosition position, final String... validExtensions) {
        this(dialogTitle, AbstractFileDirectoryChooser.DEFAULT_BUTTON_LABEL, null,
        		position, validExtensions);
    }
    
    /** adds default path. */
    public FileChooser(final String dialogTitle, final File defaultPath,
    		final ButtonPosition position, final String... validExtensions) {
        this(dialogTitle, AbstractFileDirectoryChooser.DEFAULT_BUTTON_LABEL, defaultPath, position, validExtensions);
    }
    
    /** finally: adds button label. */
    public FileChooser(final String dialogTitle, final String buttonLabel,
    		final File defaultPath, final ButtonPosition position, final String... validExtensions) {
        super(dialogTitle, defaultPath, position, buttonLabel);
        
        final Set<String> validExtensionsSet = new LinkedHashSet<String>(Arrays.asList(validExtensions));
        final String validExtensionsString = CollectionUtil.toString(validExtensionsSet);
        
        this.fileFilter = new FileFilter() {
            @Override
            public boolean accept(final File file) {
                if (file.isDirectory() == true) {
                	return true;
                }
                
                final String extension = FileUtil.extractExtension(file);
                if (extension == null) {
                    return false;
                }
                
                return validExtensionsSet.contains(extension);
            }
            @Override
            public String getDescription() {
                return validExtensionsString;
            }
        };
    }

    /**
     * @return can be null
     */
    public final File getSelectedFile() {
        return this.getFileOrDir();
    }

    @Override
    final int getSelectionMode() {
        return JFileChooser.FILES_ONLY;
    }

    @Override
    final FileFilter getFileFilter() {
        return this.fileFilter;
    }

    @Override
    final boolean isRightFileOrDir(final File file) {
        return file.isFile();
    }
    
    public final void setFile(final File file) {
        this.setFileOrDir(file);
    }

	@Override
	final String getFileDirName() {
		return "File";
	}
}
