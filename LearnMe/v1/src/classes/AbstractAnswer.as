package classes {

[Bindable]
internal class AbstractAnswer {
	
	public var correct:Boolean;
	
	public var text:String;
	
	public var answerId:uint;
	
	public function AbstractAnswer(answerId:uint) {
		this.answerId = answerId;
	}
	
	public static function parseXml(xml:XML, answer:AbstractAnswer):void {
		var correctXml:XML = xml.attribute("correct")[0] as XML;
		var correct:Boolean;
		if(correctXml == null) {
			correct = false; // by default
		} else {
			correct = correctXml.toXMLString() == "true" ? true : false;
		}
		answer.correct = correct;
		
		answer.text = XmlProcessUtil.getCDataText(xml);
		
		//trace("answer.text [" + answer.text + "] answer.correct [" + answer.correct + "]");
	}

	internal function toStringX():String {
		return "correct="+correct+";text="+text+";answerId="+answerId;
	}
}
}