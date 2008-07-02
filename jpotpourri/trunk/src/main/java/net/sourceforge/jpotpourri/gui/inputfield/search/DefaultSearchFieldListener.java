package net.sourceforge.jpotpourri.gui.inputfield.search;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class DefaultSearchFieldListener implements KeyListener, ISearchFieldListener {

	private final SearchField searchField;
	private final IDefaultSearchFieldListener listener;

    private boolean keyTyped = false;
    
	
	public DefaultSearchFieldListener(SearchField searchField, IDefaultSearchFieldListener listener) {
		this.searchField = searchField;
		this.listener = listener;
	}

    public void keyPressed(KeyEvent event) {
        this.keyTyped = false;
    }
    public void keyTyped(KeyEvent event) {
        this.keyTyped = true;
    }

    public void keyReleased(KeyEvent event) {

        if(event.getKeyCode() == KeyEvent.VK_ESCAPE) {
            // this.inpText.setText(""); will be done implicit by SearchField component
            this.listener.doResetSearch();
            return;
        }
        
        if(this.keyTyped == false && event.getKeyCode() != KeyEvent.VK_BACK_SPACE) {
            return;   
        }
        
        if(this.searchField.getText().length() == 0) {
            this.listener.doResetSearch();
            return;
        }
        

		if(this.searchField.isShowingPlaceholderText() == true) {
			this.listener.doResetSearch();
            return;
		}
		
        this.listener.doSearch(this.searchField.getText());
////        try {
//            final String oldText = this.getText();
//            String newText = null;
//            for (String suggest : this.getValues()) {
//                if(suggest.startsWith(oldText)) {
//                    newText = suggest;
//                    break;
//                }
//            }
//            
//            if(newText != null && !oldText.equals(newText)) {
////                LOG.debug("Got suggestion '" + newText + "' for input '" + oldText + "'.");
//                    this.setText(newText);
//                    this.setSelectionStart(oldText.length());
//                    this.setSelectionEnd(newText.length());
//            }
////        } catch(BusinessException e) {
////            e.printStackTrace();
////        }
    
    }

	public void didResetSearch() {
		this.listener.doResetSearch();
	}
}
