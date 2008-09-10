package classes {

import view.MultipleQuestionTestAnswerRenderer;

[Bindable]
public class MultipleChoiceQuestion extends AbstractChoiceQuestion implements INonTestQuestion {
	
	public static const TYPE:String = "MultipleChoice";
	
	public var type:String = TYPE;
	
	public var answerRenderer:Class = MultipleQuestionTestAnswerRenderer;
	
	private var _correctAnswerIds:Array;
	
	public function MultipleChoiceQuestion() {
	}
	
	public function get correctAnswerIds():Array {
		return this._correctAnswerIds;
	}
	
	
	
	internal function set correctAnswerIds(correctAnswerIds:Array):void {
		this._correctAnswerIds = correctAnswerIds;
	}
	
	public function get answerClass():Class {
		return MultipleChoiceAnswer;
	}
	
	public function asTestQuestion():ITestQuestion {
		return new MultipleChoiceTestQuestion(this);
	}
	
	public static function parseXml(xml:XML):MultipleChoiceQuestion {
		var question:MultipleChoiceQuestion = new MultipleChoiceQuestion();
		
		AbstractChoiceQuestion.parseXml(xml, question);
		
		question._correctAnswerIds = new Array();
		for each(var answer:MultipleChoiceAnswer in question.answers) {
			if(answer.correct == true) {
				question._correctAnswerIds.push(answer.answerId);
			}
		}
		
		return question;
	}
}
}