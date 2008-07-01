package net.sourceforge.jpotpourri.gui.panel.brushed;

import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

import net.sourceforge.jpotpourri.gui.JLibImageFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 
 * @author http://blog.elevenworks.com/?p=10
 */
public class BrushedMetalPanel extends TiledImagePanel {

    private static final Log LOG = LogFactory.getLog(BrushedMetalPanel.class);
    private static final long serialVersionUID = -8717131766395076544L;

    private static Image image = JLibImageFactory.getInstance().getImgBrushed();

    private HighlightedImagePanelUI ui;

    public BrushedMetalPanel() {
        super(BrushedMetalPanel.image);
    }

    @Override
    protected final boolean overridesInitializeUI() {
    	return true;
    }
    
    @Override
    protected final void overriddenInitializeUI() {
    	LOG.debug("initializeUI()");
    	this.ui = HighlightedImagePanelUI.createUI(this);
        super.setUI(this.ui);
    }

    @Override
	protected final boolean overridesSetImage(final Image pImage) {
    	return (BrushedMetalPanel.image == pImage);
    }
    
    @Override
	protected final void overriddenSetImage(final Image pImage) {
    	super.actualSetImage(pImage);
    }
    
    public final void setActive(final boolean active) {
//    	LOG.debug("setActive(active="+active+")");
    	this.ui.setActive(active);
    }
    
    


    public static void main(final String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            LOG.warn("Could not set system look and feel!", e);
        }

        final JFrame frame = new JFrame("Brushed Metal Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);
        final JPanel panel = new BrushedMetalPanel();
        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }
}
