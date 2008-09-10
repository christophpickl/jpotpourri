package classes {

import mx.collections.ArrayCollection;
	

/**
 * @private
 */
[Bindable]
internal class AbstractChoiceQuestion extends Question {
	
	/** ArrayCollection<Answer> */
	public var answers:ArrayCollection = new ArrayCollection();
	
	public function AbstractChoiceQuestion() {
	}


	internal static function parseXml(xml:XML, question:IGenericQuestion):void {
		question.text = XmlProcessUtil.getCDataNodeText(xml, "Text");
		
		question.title = (xml.attribute("title")[0] as XML).toXMLString();
		
		var answerClass:* = question.answerClass;
		
		var answers:ArrayCollection = new ArrayCollection();
		var answerId:uint = 0;
		for each(var answerXml:XML in (xml.child("Answers")[0] as XML).children()) {
			answers.addItem(answerClass.parseXml(answerXml, answerId));
			answerId++;
		}
		question.answers = answers;
	}
}
}