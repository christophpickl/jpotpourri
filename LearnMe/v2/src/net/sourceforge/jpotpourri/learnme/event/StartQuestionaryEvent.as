package net.sourceforge.jpotpourri.learnme.event {

import flash.events.Event;

import net.sourceforge.jpotpourri.learnme.vo.IQuestionCatalog;
	

public class StartQuestionaryEvent extends Event{
	
	public static const TYPE: String = "startquest";
	
	public static const QUESTIONS_ALL: int = 1;
	public static const QUESTIONS_LIMITED_BY_WEIGHT: int = 2;
	
	public var questionCatalog: IQuestionCatalog;
	
	public var questionsSelection: int;
	
	public function StartQuestionaryEvent(catalog: IQuestionCatalog, questionsSelection: int, bubbles:Boolean=false, cancelable:Boolean=false) {
		super(TYPE, bubbles, cancelable);
		this.questionCatalog = catalog;
		this.questionsSelection = questionsSelection;
	}
	

}
}