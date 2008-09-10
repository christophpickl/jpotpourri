package classes {

import mx.controls.RadioButtonGroup;

import view.SingleQuestionTestAnswerRenderer;

[Bindable]
public class SingleChoiceQuestion extends AbstractChoiceQuestion implements INonTestQuestion {
	
	public static const TYPE:String = "SingleChoice";
	
	public var type:String = TYPE;
	
	public var answerRenderer:Class = SingleQuestionTestAnswerRenderer;
	
	private var _correctAnswerId:uint;
	
	public var answerButtonGroup:RadioButtonGroup = new RadioButtonGroup();
	
	public function SingleChoiceQuestion() {
		
	}
	
	public function get correctAnswerId():uint {
		return this._correctAnswerId;
	}
	
	internal function set correctAnswerId(correctAnswerId:uint):void {
		this._correctAnswerId = correctAnswerId;
	}
	
	public function get answerClass():Class {
		return SingleChoiceAnswer;
	}
	
	public function asTestQuestion():ITestQuestion {
		return new SingleChoiceTestQuestion(this);
	}

	public static function parseXml(xml:XML):SingleChoiceQuestion {
		var question:SingleChoiceQuestion = new SingleChoiceQuestion();
		
		AbstractChoiceQuestion.parseXml(xml, question);
		
		for each(var answer:SingleChoiceAnswer in question.answers) {
			
			answer.answerButtonGroup = question.answerButtonGroup;
			
			if(answer.correct == true) {
				question._correctAnswerId = answer.answerId;
				break;
			}
		}
		
		return question;
	}
}
}