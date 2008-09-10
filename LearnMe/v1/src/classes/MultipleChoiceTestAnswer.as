package classes {

[Bindable]
public class MultipleChoiceTestAnswer extends MultipleChoiceAnswer implements ITestAnswer {
	
	public var userSelected:Boolean = false;
	
	public function MultipleChoiceTestAnswer(answer:MultipleChoiceAnswer) {
		super(answer.answerId);
		this.correct = answer.correct;
		this.text = answer.text;
	}

	public override function toString():String {
		return "MultipleChoiceAnswer[" + this.toStringX() + ";userSelected="+userSelected+"]";
	}
	
}
}