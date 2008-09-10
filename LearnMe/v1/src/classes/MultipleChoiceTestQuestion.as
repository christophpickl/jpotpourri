package classes {

import mx.collections.ArrayCollection;
import mx.controls.List;


[Bindable]
public class MultipleChoiceTestQuestion extends MultipleChoiceQuestion implements ITestQuestion {
	
	/** ArrayCollection<MultipleChoiceTestAnswer> */
	public var testAnswers:ArrayCollection = new ArrayCollection();
	
	public function MultipleChoiceTestQuestion(question:MultipleChoiceQuestion) {
		this.answerRenderer = question.answerRenderer;
		this.answers = question.answers;
		this.correctAnswerIds = question.correctAnswerIds;
		this.text = question.text;
		this.title = question.title;
		
		for each(var answer:IAnswer in question.answers) {
			this.testAnswers.addItem(answer.asTestAnswer());
		}
		
	}

	public function hasUserCorrect():Boolean {
		var selectedAnswerIds:Array = new Array();
		
		for each (var _testAnswer:Object in this.testAnswers) {
			var testAnswer:MultipleChoiceTestAnswer = _testAnswer as MultipleChoiceTestAnswer;
			if(testAnswer.userSelected == true) {
				selectedAnswerIds.push(testAnswer.answerId);
			}
		}
		
		trace("MultipleChoiceTestQuestion.hasUserCorrect() only checking for same length. "+
			  "(correct:"+this.correctAnswerIds.length+" == selected:"+selectedAnswerIds.length+")");
		return this.correctAnswerIds.length == selectedAnswerIds.length;
	}
}
}