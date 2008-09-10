package classes {

import mx.controls.RadioButtonGroup;
	
[Bindable]
public class SingleChoiceAnswer extends AbstractAnswer implements IAnswer {
	
	public var answerButtonGroup:RadioButtonGroup;
	
	public function SingleChoiceAnswer(answerId:uint) {
		super(answerId);
	}
	
	public function asTestAnswer():ITestAnswer {
		return new SingleChoiceTestAnswer(this);
	}


	public static function parseXml(xml:XML, answerId:uint):SingleChoiceAnswer {
		const answer:SingleChoiceAnswer = new SingleChoiceAnswer(answerId);
		AbstractAnswer.parseXml(xml, answer);
		return answer;
	}
	
	public function toString():String {
		return "SingleChoiceAnswer[" + this.toStringX() + "]";
	}
}
}