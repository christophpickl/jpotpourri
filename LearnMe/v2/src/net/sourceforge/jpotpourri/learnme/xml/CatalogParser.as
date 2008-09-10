package net.sourceforge.jpotpourri.learnme.xml {

import flash.xml.XMLDocument;

import net.sourceforge.jpotpourri.learnme.vo.IQuestionCatalog;

public class CatalogParser {
	
	public function CatalogParser() {
	}

	public static function parse(xmlString: String): IQuestionCatalog {
		var xmlDoc: XMLDocument = new XMLDocument();
		xmlDoc.parseXML(xmlString);
		
		
		return null;
	} 
}
}