package classes {

[Bindable]
public class MultipleChoiceAnswer extends AbstractAnswer implements IAnswer {
	
	public function MultipleChoiceAnswer(answerId:uint) {
		super(answerId);
	}
	
	public function asTestAnswer():ITestAnswer {
		return new MultipleChoiceTestAnswer(this);
	}

	public static function parseXml(xml:XML, answerId:uint):MultipleChoiceAnswer {
		const answer:MultipleChoiceAnswer = new MultipleChoiceAnswer(answerId);
		AbstractAnswer.parseXml(xml, answer);
		return answer;
	}
	
	//internal override function toStringX():String {
	//	return "correct="+correct+";text="+text+";answerId="+answerId;
	//}
	
	public function toString():String {
		return "MultipleChoiceAnswer[" + this.toStringX() + "]";
	}
	
}
}