package net.sourceforge.jpotpourri.gui.chooser;

import java.io.File;

/**
 * 
 * @author christoph_pickl@users.sourceforge.net
 */
public interface IFileDirectoryChooserListener {

    /**
     * gets invoked if user has choosen directory and approved operation.
     * @param dir is never null
     */
    void doChoosen(File dir);
    
}
