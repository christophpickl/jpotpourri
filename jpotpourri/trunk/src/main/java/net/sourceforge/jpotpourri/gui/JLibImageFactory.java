package net.sourceforge.jpotpourri.gui;

import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

import org.apache.log4j.Logger;

/**
 * 
 * @author christoph_pickl@users.sourceforge.net
 */
public final class JLibImageFactory {

    private static final Logger LOG = Logger.getLogger(JLibImageFactory.class);

	private static final JLibImageFactory INSTANCE = new JLibImageFactory();

    private static final String PATH_IMAGES = "/images/";
    
    private final Map<String, ImageIcon> iconsCache = new HashMap<String, ImageIcon>();
    
	
	private JLibImageFactory() {
		// singletion
	}
	
	public static JLibImageFactory getInstance() {
		return INSTANCE;
	}
    

    private ImageIcon getImage(final String filename) {
        LOG.debug("Getting image by filename '" + filename + "'.");

        final ImageIcon image;

        final ImageIcon cachedIcon = this.iconsCache.get(filename);
        if (cachedIcon != null) {
            image = cachedIcon;
        } else {
            final String imagePath = PATH_IMAGES + filename;
            LOG.debug("Loading and caching image for first time by path '" + imagePath + "'...");
            
            final URL imageUrl = JLibImageFactory.class.getResource(imagePath);
            if (imageUrl == null) {
                throw new RuntimeException("Could not load image (filename='" + filename + "') " +
                		"by image path '" + imagePath + "'!");
            }
            
            LOG.debug("Loaded image (" + filename + ") by URL '" + imageUrl.getFile() + "'.");
            image = new ImageIcon(Toolkit.getDefaultToolkit().getImage(imageUrl));
            this.iconsCache.put(filename, image);
        }

        return image;
    }

 

    public Image getImgBrushed() {
        return this.getImage("brushed.gif").getImage();
    }


    public ImageIcon getDialogWarning() {
        return this.getImage("dialog/warning.png");
    }
    
    public ImageIcon getDialogError() {
        return this.getImage("dialog/error.png");
    }
	
}
