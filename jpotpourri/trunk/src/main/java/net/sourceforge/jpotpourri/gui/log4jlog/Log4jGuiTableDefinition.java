package net.sourceforge.jpotpourri.gui.log4jlog;

import java.awt.Color;

import net.sourceforge.jpotpourri.gui.IMacColors;

public class Log4jGuiTableDefinition implements IMacColors {

	private final int rows;
	public static final int DEFAULT_ROWS = -1;
	private final Color colorRowBackgroundEven;
	private static final Color DEFAULT_COLOR_EVEN = MAC_COLOR_ROW_BACKGROUND_EVEN;
	private final Color colorRowBackgroundOdd;
	private static final Color DEFAULT_COLOR_ODD = MAC_COLOR_ROW_BACKGROUND_ODD;

	private final Color colorRowForeground;
	private static final Color DEFAULT_COLOR_ROW_FOREGROUND = MAC_COLOR_ROW_FOREGROUND;
	private final Color colorSelectedRowForeground;
	private static final Color DEFAULT_COLOR_SELECTED_FOREGROUND = MAC_COLOR_SELECTED_ROW_FOREGROUND;
	private final Color colorSelectedRowBackground;
	private static final Color DEFAULT_COLOR_SELECTED_BACKGROUND = MAC_COLOR_SELECTED_ROW_BACKGROUND;
//	private final Color colorSelectedNofocusRowBackground;
//	private static final Color DEFAULT_COLOR_SELECTED_NOFOCUS_BG = MAC_COLOR_SELECTED_NOFOCUS_BG;
	
	
	public Log4jGuiTableDefinition(
			Integer rows,
			Color colorRowBackgroundEven,
			Color colorRowBackgroundOdd,
			Color colorRowForeground,
			Color colorSelectedRowForeground,
			Color colorSelectedRowBackground
//			Color colorSelectedNofocusRowBackground
			) {

		this.rows = rows != null ? rows.intValue() : DEFAULT_ROWS;
		this.colorRowBackgroundEven = colorRowBackgroundEven != null ? colorRowBackgroundEven : DEFAULT_COLOR_EVEN;
		this.colorRowBackgroundOdd = colorRowBackgroundOdd != null ? colorRowBackgroundOdd : DEFAULT_COLOR_ODD;
		this.colorRowForeground = colorRowForeground != null ? colorRowForeground : DEFAULT_COLOR_ROW_FOREGROUND;
		this.colorSelectedRowForeground = colorSelectedRowForeground != null ? colorSelectedRowForeground : DEFAULT_COLOR_SELECTED_FOREGROUND;
		this.colorSelectedRowBackground = colorSelectedRowBackground != null ? colorSelectedRowBackground : DEFAULT_COLOR_SELECTED_BACKGROUND;
//		this.colorSelectedNofocusRowBackground = colorSelectedNofocusRowBackground != null ? colorSelectedNofocusRowBackground : DEFAULT_COLOR_SELECTED_NOFOCUS_BG;
		
		
	}

	public String toString() {
		return "Log4jGuiTableDefinition[colorEven=" + this.colorRowBackgroundEven + ";colorOdd=" + this.colorRowBackgroundOdd + ";rows=" + this.rows + ";]";
	}
	

	public int getRows() {
		return this.rows;
	}
	public Color getColorRowForeground() {
		return colorRowForeground;
	}

	public Color getColorSelectedRowForeground() {
		return colorSelectedRowForeground;
	}

	public Color getColorSelectedRowBackground() {
		return colorSelectedRowBackground;
	}
	
	public Color getColorRowBackgroundEven() {
		return this.colorRowBackgroundEven;
	}
	public Color getColorRowBackgroundOdd() {
		return this.colorRowBackgroundOdd;
	}
//	public Color getColorSelectedNofocusRowBackground() {
//		return this.colorSelectedNofocusRowBackground;
//	}
	
}
