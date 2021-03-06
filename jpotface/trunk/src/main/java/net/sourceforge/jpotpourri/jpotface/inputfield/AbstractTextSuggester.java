/*
 * OurMovies - Yet another movie manager
 * Copyright (C) 2008 Christoph Pickl (christoph_pickl@users.sourceforge.net)
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package net.sourceforge.jpotpourri.jpotface.inputfield;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

import javax.swing.JTextField;

/**
 * 
 * @author christoph_pickl@users.sourceforge.net
 */
public abstract class AbstractTextSuggester extends JTextField implements KeyListener {
    
//    private static final Logger LOG = Logger.getLogger(AbstractTextSuggester.class);
    
    private static final long serialVersionUID = 3469670835562449054L;
    
//    static final IMovieDao DAO = BeanFactory.getInstance().getMovieDao();
    
    private boolean keyTyped = false;
    
    

    public AbstractTextSuggester(final int columns) {
        super(columns);
        this.addKeyListener(this);
    }
    
    public AbstractTextSuggester() {
        this.addKeyListener(this);
    }
    

    protected abstract List<String> getValues();
    
    
    public void keyPressed(KeyEvent event) {
        this.keyTyped = false;
    }
    public void keyTyped(KeyEvent event) {
        this.keyTyped = true;
    }

    public void keyReleased(KeyEvent event) {
        if(this.keyTyped == false) {
            return;   
        }
        if(this.getText().length() == 0) {
            return;
        }
        if(event.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
            return;
        }
        
        final String oldText = this.getText();
        String newText = null;
        for (String suggest : this.getValues()) {
            if(suggest.startsWith(oldText)) {
                newText = suggest;
                break;
            }
        }
        
        if(newText != null && !oldText.equals(newText)) {
//            LOG.debug("Got suggestion '" + newText + "' for input '" + oldText + "'.");
            this.setText(newText);
            this.setSelectionStart(oldText.length());
            this.setSelectionEnd(newText.length());
        }
    
    }

}
