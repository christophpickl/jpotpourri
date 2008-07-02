package net.sourceforge.jpotpourri.gui.log4jlog;

import java.awt.Color;

public class Log4jGuiTableDefinition {
	
	private final Color colorEven;
	private final Color colorOdd;
	private static final Color DEFAULT_COLOR_EVEN = Color.WHITE;
	private static final Color DEFAULT_COLOR_ODD = Color.GRAY;
	private final int rows;
	public static final int DEFAULT_ROWS = -1;
	
	public Log4jGuiTableDefinition(Color colorEven, Color colorOdd, Integer rows) {

		this.colorEven = colorEven != null ? colorEven : DEFAULT_COLOR_EVEN;
		this.colorOdd = colorOdd != null ? colorOdd : DEFAULT_COLOR_ODD;
		this.rows = rows != null ? rows.intValue() : DEFAULT_ROWS;
	}
	
	public String toString() {
		return "Log4jGuiTableDefinition[colorEven=" + this.colorEven + ";colorOdd=" + this.colorOdd + ";rows=" + this.rows + ";]";
	}
	
	public Color getColorEven() {
		return this.colorEven;
	}
	public Color getColorOdd() {
		return this.colorOdd;
	}
	public int getRows() {
		return this.rows;
	}
	
}
