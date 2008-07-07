package net.sourceforge.jpotpourri.gui.flexi;


import java.awt.Dimension;
import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;





/**
 * 
 * Flexi Combo box class porvides options for showing Horizontal Scrollbar, View
 * 
 * Tips and Widening the Popup
 * 
 * 
 * 
 * @author Vimal; http://javabyexample.wisdomplug.com/java-concepts/34-core-java/59-tips-and-tricks-for-jtree-jlist-and-jcombobox-part-i.html
 * 
 * 
 * 
 */

public class FlexiComboBox extends JComboBox

{



	/**
	 * 
	 */
	private static final long serialVersionUID = -6636780961012342920L;



	/**
	 * 
	 * Whether ViewTips should be shown.
	 * 
	 */

	private boolean showTips = false;



	/**
	 * 
	 * Set the popup Width
	 * 
	 */

	private int popupWidth = 0;



	/**
	 * 
	 * Keep track of whether layout is happening
	 * 
	 */

	private boolean layingOut = false;



	/**
	 * 
	 * Display the horizontal scrollbar
	 * 
	 */

	private boolean showHSCroller = false;



	/**
	 * 
	 * Default Constructor Creates a default flexi Combobox
	 * 
	 */

	public FlexiComboBox()

	{

		super();

		setUI(new FlexiComboUI());

	}



	/**
	 * 
	 * Creates a Flexi Combobox with the given Model
	 * 
	 * 
	 * 
	 * @param model
	 * 
	 * The Combobox Model
	 * 
	 */

	public FlexiComboBox(ComboBoxModel model)

	{

		super(model);

		setUI(new FlexiComboUI());

	}





	/**
	 * 
	 * Creates a Flexi Combobox with the given items
	 * 
	 * 
	 * 
	 * @param items
	 * 
	 * the Items to be added to the Combo Box
	 * 
	 */

	public FlexiComboBox(Object[] items)

	{

		super(items);

		setUI(new FlexiComboUI());

	}



	/**
	 * 
	 * Creates a Flexi Combobox with the given items
	 * 
	 * 
	 * 
	 * @param items
	 * 
	 * the Items to be added to the Combo Box
	 * 
	 */

	public FlexiComboBox(Vector<?> items)

	{

		super(items);

		setUI(new FlexiComboUI());

	}







	/**
	 * 
	 * Overriden to handle the popup Size
	 * 
	 */

	public void doLayout()

	{

		try

		{

			layingOut = true;

			super.doLayout();

		} finally

		{

			layingOut = false;

		}

	}



	/**
	 * 
	 * Overriden to handle the popup Size
	 * 
	 */

	public Dimension getSize()

	{

		Dimension dim = super.getSize();

		if (!layingOut && popupWidth != 0)

			dim.width = popupWidth;

		return dim;

	}



	/**
	 * 
	 * @return the showTips
	 * 
	 */

	public boolean isShowTips()

	{

		return showTips;

	}



	/**
	 * 
	 * @param showTips
	 * 
	 * the showTips to set
	 * 
	 */

	public void setShowTips(boolean showTips)

	{

		this.showTips = showTips;

		if(showTips)

		{

			ViewTooltips.register(((FlexiComboUI)getUI()).getPopup().getList());

		}

		else

		{

			ViewTooltips.unregister(((FlexiComboUI)getUI()).getPopup().getList());

		}

	}



	/**
	 * 
	 * @return the popupWidth
	 * 
	 */

	public int getPopupWidth()

	{

		return popupWidth;

	}



	/**
	 * 
	 * @param popupWidth
	 * 
	 * the popupWidth to set
	 * 
	 */

	public void setPopupWidth(int popupWidth)

	{

		this.popupWidth = popupWidth;

	}



	/**
	 * 
	 * @return the showHSCroller
	 * 
	 */

	public boolean isShowHSCroller()

	{

		return showHSCroller;

	}



	/**
	 * 
	 * @param showHSCroller
	 * 
	 * the showHSCroller to set
	 * 
	 */

	public void setShowHSCroller(boolean showHSCroller)

	{

		this.showHSCroller = showHSCroller;

		if(showHSCroller)

		{

			((FlexiComboUI)getUI()).getPopup().getScrollPane().setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		}

		else

		{

			((FlexiComboUI)getUI()).getPopup().getScrollPane().setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		}

	}



	/**
	 * 
	 * 
	 * 
	 * @author Vimal
	 * 
	 * 
	 * 
	 */

	private class FlexiComboUI extends BasicComboBoxUI

	{

		protected ComboPopup createPopup()

		{

			return new FlexiComboPopup(comboBox);

		}

		public FlexiComboPopup getPopup()

		{

			return (FlexiComboPopup)popup;

		}

	}



	private class FlexiComboPopup extends BasicComboPopup

	{



		private Dimension size = null;



		public FlexiComboPopup(JComboBox combo)

		{

			super(combo);

			if(showTips)

			{

				ViewTooltips.register(list);

			}

			else

			{

				ViewTooltips.unregister(list);

			}



		}



		public JScrollPane createScroller()

		{

			JScrollPane scroller = null;



			if(showHSCroller)

			{

				scroller = new JScrollPane(list,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,

						JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

			}

			else

			{

				scroller = new JScrollPane(list,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,

						JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

			}

			return scroller;

		}



		public JScrollPane getScrollPane()

		{

			return scroller;

		}







		public JList getList()

		{

			return list;

		}



	}







}

