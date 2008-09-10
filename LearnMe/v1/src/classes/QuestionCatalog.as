package classes {

import mx.collections.ArrayCollection;
	
[Bindable]
public class QuestionCatalog {
	
	public var title:String;
	
	/** ArrayCollection<INonTestQuestion> */
	public var questions:ArrayCollection;
	
	
	public function QuestionCatalog(title:String, questions:ArrayCollection) {
		this.title = title;
		this.questions = questions;
	}

	public static function parseXml(xml:XML):QuestionCatalog {
		var title:String = XmlProcessUtil.getAttributeText(xml, "title");
		var questions:ArrayCollection = new ArrayCollection();
		
		trace("xml.children().length() = " + xml.children().length());
		for each(var questionXml:XML in xml.children()) {
			questions.addItem(XmlProcessUtil.newQuestion(questionXml));
		}
		
		return new QuestionCatalog(title, questions);
	}
}
}