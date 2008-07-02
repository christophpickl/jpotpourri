package net.sourceforge.jpotpourri.gui.log4jlog;

import java.awt.Color;

class PropertiesTable {

		private String rows;
		private String colorEven;
		private String colorOdd;
		 // FIXME make configurable: selectedBgColor, selectedFontColor, fontColor
		private String fontColor;
		private String selectedFontColor;
		private String selectedBackgroundColor;
		
		public PropertiesTable() {
			// nothing to do
		}

		public Log4jGuiTableDefinition toDefinition() {
			Color newColorEven = null;
			if(this.colorEven != null) {
				newColorEven = parseColorString(this.colorEven);
				if(newColorEven == null) {
					Er.or(" Invalid tableColorEven attribute value [" + this.colorEven + "]!");
				}
			}
			Color newColorOdd = null;
			if(this.colorEven != null) {
				newColorOdd = parseColorString(this.colorOdd);
				if(newColorOdd == null) {
					Er.or("Invalid tableColorOdd attribute value [" + this.colorEven + "]!");
				}
			}
			Integer newRows = null;
			if(this.rows != null) {
				newRows = parseNumberString(this.rows, 0, 9999);
				if(newRows == null) {
					Er.or("Invalid tableRows attribute value [" + this.rows + "]!");
				}
			}
			
			
			return new Log4jGuiTableDefinition(
					newColorEven,
					newColorOdd,
					newRows);
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
		
		
		public String getColorEven() {
			return this.colorEven;
		}
		public void setColorEven(String colorEven) {
			this.colorEven = colorEven;
		}
		public String getColorOdd() {
			return this.colorOdd;
		}
		public void setColorOdd(String colorOdd) {
			this.colorOdd = colorOdd;
		}
		public String getRows() {
			return this.rows;
		}
		public void setRows(String rows) {
			this.rows = rows;
		}
}
