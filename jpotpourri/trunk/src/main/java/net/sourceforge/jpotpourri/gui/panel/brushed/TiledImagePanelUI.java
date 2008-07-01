package net.sourceforge.jpotpourri.gui.panel.brushed;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicPanelUI;


/**
 * 
 * @author http://blog.elevenworks.com/?p=10
 */
public abstract class TiledImagePanelUI extends BasicPanelUI implements PropertyChangeListener {
	
    private static Image image;

    public TiledImagePanelUI() {
        /* nothing to do */
    }

    public TiledImagePanelUI(final Image aImage) {
        image = aImage;
    }

    @SuppressWarnings("unused")
    public static ComponentUI createUI(final JComponent component) {
        return new DefaultTiledImagePanelUI();
    }

    @Override
	public final void installUI(final JComponent c) {
        super.installUI(c);

        if (image == null) {
            if (c instanceof TiledImagePanel) {
                image = ((TiledImagePanel) c).getImage();
                c.addPropertyChangeListener("image", this);
            } else {
                throw new RuntimeException("TiledImagePanelUI must be used with a TiledImagePanel or " +
                		"the image must be set explicitly");
            }
        }
    }

    @Override
	protected final void uninstallDefaults(final JPanel p) {
        p.removePropertyChangeListener("image", this);
        super.uninstallDefaults(p);
    }

    // -----------------------------------------------------------------------------------------------------------------
    //  Custom painting methods
    // -----------------------------------------------------------------------------------------------------------------

    protected abstract boolean overridesPaint();
    protected abstract void overriddenPaint(final Graphics g, final JComponent c);
    
    protected final void actualPaint(final Graphics g, final JComponent c) {
    	if (image == null) {
            return;
        }
        Dimension vSize = c.getSize();

        Graphics2D g2d = (Graphics2D) g;

        for (int x = 0; x < vSize.width; x += 200) {
            for (int y = 0; y < vSize.height; y += 200) {
                g2d.drawImage(image, x, y, null);
            }
        }
    }
    
    @Override
	public final void paint(final Graphics g, final JComponent c) {
        if(this.overridesPaint()) {
        	this.overriddenPaint(g, c);
        } else {
        	this.actualPaint(g, c);
        }
    }

    // -----------------------------------------------------------------------------------------------------------------
    //  Implementation of PropertyChangeListener interface
    // -----------------------------------------------------------------------------------------------------------------

    public final void propertyChange(final PropertyChangeEvent evt) {
        if ("image".equals(evt.getPropertyName())) {
            image = (Image) evt.getNewValue();
        }
    }
    

    /**
     * @author christoph_pickl@users.sourceforge.net
     */
    public static class DefaultTiledImagePanelUI extends TiledImagePanelUI {
		@Override
		@SuppressWarnings("unused")
		protected final void overriddenPaint(final Graphics g, final JComponent c) {
			// nothing to do
		}
		@Override
		protected final boolean overridesPaint() {
			return false;
		}
    	
    }
}
