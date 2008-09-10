package classes {

import flash.events.Event;

import mx.collections.ArrayCollection;

[Bindable]	
public class SingleChoiceTestQuestion extends SingleChoiceQuestion implements ITestQuestion {
	
	public var testAnswers:ArrayCollection = new ArrayCollection();
	
	private var _prevSelectedAnswer:SingleChoiceTestAnswer;
	
	public function SingleChoiceTestQuestion(question:SingleChoiceQuestion) {
		this.answerRenderer = question.answerRenderer;
		this.answers = question.answers;
		this.correctAnswerId = question.correctAnswerId;
		this.text = question.text;
		this.title = question.title;
		this.answerButtonGroup = question.answerButtonGroup;
		
		for each(var answer:IAnswer in question.answers) {
			this.testAnswers.addItem(answer.asTestAnswer());
		}
		
		this.answerButtonGroup.addEventListener(Event.CHANGE, onButtonChange);
	}
	
	private function onButtonChange(event:Event):void {
		if(_prevSelectedAnswer != null) {
			_prevSelectedAnswer.userSelected = false;
		}
		var answer:SingleChoiceTestAnswer = this.answerButtonGroup.selectedValue as SingleChoiceTestAnswer;
		answer.userSelected = true;
		
		this._prevSelectedAnswer = answer;
	}

	public function hasUserCorrect():Boolean {
		var selectedAnswerId:int = -1;
		for each(var _testAnswer:Object in this.testAnswers) {
			var testAnswer:SingleChoiceTestAnswer = _testAnswer as SingleChoiceTestAnswer;
			if(testAnswer.userSelected) {
				selectedAnswerId = testAnswer.answerId;
				break; 
			}
		}
		
		// selectedAnswer == -1 if nothing was selected
		trace("SingleChoiceTestQuestion.hasUserCorrect() selectedAnswerId: "+selectedAnswerId+"; correctAnswerId:"+correctAnswerId);
		return selectedAnswerId == this.correctAnswerId;
	}
}
}