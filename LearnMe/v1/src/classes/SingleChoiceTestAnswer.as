package classes {

import mx.controls.RadioButtonGroup;


[Bindable]
public class SingleChoiceTestAnswer extends SingleChoiceAnswer implements ITestAnswer {
	
	// public var userSelected:Boolean = false;
	private var _userSelected:Boolean = false;
	
	public function SingleChoiceTestAnswer(answer:SingleChoiceAnswer) {
		super(answer.answerId);
		this.correct = answer.correct;
		this.text = answer.text;
		this.answerButtonGroup = answer.answerButtonGroup;
	}

	
	public function get userSelected():Boolean {
		return this._userSelected;
	}
	
	public function set userSelected(userSelected:Boolean):void {
		trace("SingleChoiceTestAnswer["+this.answerId+"].userSelected = " + userSelected + "(was: " + this._userSelected + ")");
		this._userSelected = userSelected;
	}
	
	
	public override function toString():String {
		return "SingleChoiceTestAnswer[" + this.toStringX() + ";userSelected="+userSelected+"]";
	}
}
}