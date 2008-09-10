package net.sourceforge.jpotpourri.learnme {

import flash.text.StyleSheet;

public class Style {
	public function Style() {
	}

	private static var XML_STYLE:StyleSheet;
	
	public static function get xmlStyle():StyleSheet {
		if(XML_STYLE == null) {
			XML_STYLE = new StyleSheet();
			XML_STYLE.parseCSS("code { font-family:Courier; }");
		}
		return XML_STYLE;
	}

}
}