package net.sourceforge.jpotpourri.learnme.event {

import flash.events.Event;

import net.sourceforge.jpotpourri.learnme.vo.IQuestionCatalog;
	

public class StartQuestionaryEvent extends Event{
	
	public static const TYPE: String = "startquest";
	
	public var questionCatalog: IQuestionCatalog;
	
	public function StartQuestionaryEvent(catalog: IQuestionCatalog, bubbles:Boolean=false, cancelable:Boolean=false) {
		super(TYPE, bubbles, cancelable);
		this.questionCatalog = catalog;
	}
	

}
}