package classes {
	import mx.charts.chartClasses.ChartBase;
	

internal class XmlProcessUtil {
	
	private static var QUESTION_TYPE_MAPPING:Array;
	
	private static var wasStaticInitialized:Boolean = staticInitializer();
	
	private static function staticInitializer():Boolean {
		if(QUESTION_TYPE_MAPPING == null) {
			//trace("Initializing question type map...");
			QUESTION_TYPE_MAPPING = new Array();
			QUESTION_TYPE_MAPPING[MultipleChoiceQuestion.TYPE] = MultipleChoiceQuestion;
			QUESTION_TYPE_MAPPING[SingleChoiceQuestion.TYPE] = SingleChoiceQuestion;
		}
		return true;
	}
	
	public function XmlProcessUtil() {
	}

	internal static function getAttributeText(xml:XML, attributeName:String):String {
		return (xml.attribute(attributeName)[0] as XML).toXMLString();
	}

	internal static function newQuestion(questionXml:XML):INonTestQuestion {
		var questionType:String = getAttributeText(questionXml, "type");
		//trace("questionType: ["+questionType+"]");
		
		var questionClass:* = QUESTION_TYPE_MAPPING[questionType];
		if(questionClass == null) {
			throw new Error("Unhandled question type [" + questionType + "]!");
		}
		
		return questionClass.parseXml(questionXml); // const question:Question =
		
	}


	internal static function getCDataText(node:XML):String {
		return removeCData((node.text()[0] as XML).toXMLString());
	}

	internal static function getCDataNodeText(xml:XML, nodeName:String):String {
		var string:String = (xml.child(nodeName)[0] as XML).text().toXMLString();
		return removeCData(string);
	}

	private static function removeCData(input:String):String {
		if(input.substr(0, 9) == "<![CDATA[") {
			input = input.substr(9);
			input = input.substr(0, input.length - 3);
		}
		return input;
	}
	
}
}