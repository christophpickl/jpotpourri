package net.sourceforge.jpotpourri.gui.log4jlog;

import java.awt.Color;

class PropertiesTable {

	private String tableRows;
	private String colorRowBackgroundEven;
	private String colorRowBackgroundOdd;
	private String colorRowForeground;
	private String colorSelectedRowForeground;
	private String colorSelectedRowBackground;
//	private String colorSelectedNofocusRowBackground;
	
	public PropertiesTable() {
		// nothing to do
	}

	public Log4jGuiTableDefinition toDefinition() {

		Integer newTableRows = null;
		if(this.tableRows != null) {
			newTableRows = parseNumberString(this.tableRows, 0, 9999);
			if(newTableRows == null) {
				Err.or("Invalid tableRows attribute value [" + this.tableRows + "]!");
			}
		}
		
		final Color newRowBgColorEven = safeParseColor("tableColorEven", this.colorRowBackgroundEven);
		final Color newRowBgColorOdd = safeParseColor("tableColorOdd", this.colorRowBackgroundOdd);
		final Color newRowFgColor = safeParseColor("fontColor", this.colorRowForeground);
		final Color newSelectedRowFgColor = safeParseColor("selectedFontColor", this.colorSelectedRowForeground);
		final Color newSelectedRowBgColor = safeParseColor("selectedBackgroundColor", this.colorSelectedRowBackground);
//		final Color newSelectedNofocusRowBgColor = safeParseColor("colorSelectedNofocusRowBackground", this.colorSelectedNofocusRowBackground);
		
		
		
		
		return new Log4jGuiTableDefinition(
				newTableRows,
				newRowBgColorEven,
				newRowBgColorOdd,
				newRowFgColor,
				newSelectedRowFgColor,
				newSelectedRowBgColor
//				newSelectedNofocusRowBgColor
				);
	}
	
	private static Color safeParseColor(String attributeName, String attributeValue) {
		Color color = null;
		if(attributeValue != null) {
			color = parseColorString(attributeValue);
			if(color == null) {
				Err.or("Invalid "+attributeName+" attribute value [" + attributeValue + "]!");
			}
		}
		return color;
	}

	private static Integer parseNumberString(String numberString, int lowerInclusive, int upperInclusive) {
		assert(numberString != null);
		final int i;
		try {
			i = Integer.parseInt(numberString.trim());
		} catch(NumberFormatException e) {
			return null;
		}
		if(i < lowerInclusive || i > upperInclusive) {
			return null;
		}
		
		return new Integer(i);
	}

	private static Color parseColorString(String colorString) {
		assert(colorString != null);
		if(colorString.indexOf(",") == -1) {
			return null;
		}
		final String[] parts = colorString.split(",");
		if(parts.length != 3) {
			return null;
		}
		
		final int[] rgb = new int[3];
		for (int i = 0; i < parts.length; i++) {
			try {
				final int anyRgb = Integer.parseInt(parts[i].trim());
				if(anyRgb < 0 || anyRgb > 255) {
					return null;
				}
				rgb[i] = anyRgb;
			} catch(NumberFormatException e) {
				return null;
			}
		}
		return new Color(rgb[0], rgb[1], rgb[2]);
	}
	
	
	public String getColorRowBackgroundEven() {
		return this.colorRowBackgroundEven;
	}
	public void setColorRowBackgroundEven(String colorEven) {
		this.colorRowBackgroundEven = colorEven;
	}
	public String getColorRowBackgroundOdd() {
		return this.colorRowBackgroundOdd;
	}
	public void setColorRowBackgroundOdd(String colorOdd) {
		this.colorRowBackgroundOdd = colorOdd;
	}
	public String getTableRows() {
		return this.tableRows;
	}
	public void setTableRows(String rows) {
		this.tableRows = rows;
	}

	public String getColorRowForeground() {
		return colorRowForeground;
	}

	public void setColorRowForeground(String fontColor) {
		this.colorRowForeground = fontColor;
	}

	public String getColorSelectedRowForeground() {
		return colorSelectedRowForeground;
	}

	public void setColorSelectedRowForeground(String selectedFontColor) {
		this.colorSelectedRowForeground = selectedFontColor;
	}

	public String getColorSelectedRowBackground() {
		return colorSelectedRowBackground;
	}

	public void setColorSelectedRowBackground(String selectedBackgroundColor) {
		this.colorSelectedRowBackground = selectedBackgroundColor;
	}

//	public String getColorSelectedNofocusRowBackground() {
//		return colorSelectedNofocusRowBackground;
//	}
//
//	public void setColorSelectedNofocusRowBackground(String colorSelectedNofocusRowBackground) {
//		this.colorSelectedNofocusRowBackground = colorSelectedNofocusRowBackground;
//	}
	
	
}
