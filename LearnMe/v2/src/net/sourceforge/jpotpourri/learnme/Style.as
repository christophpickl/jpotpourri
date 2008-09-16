package net.sourceforge.jpotpourri.learnme {

import flash.text.StyleSheet;

public class Style {
	
	private static const CSS_CODE: String = "" +
		"code { " +
		  "font-family:Courier; " +
		"}";
	// FIXME no line break afterwards!
	
	public function Style() {
	}

	private static var XML_STYLE:StyleSheet;
	
	public static function get xmlStyle():StyleSheet {
		if(XML_STYLE == null) {
			XML_STYLE = new StyleSheet();
			XML_STYLE.parseCSS(CSS_CODE);
		}
		return XML_STYLE;
	}

}
}