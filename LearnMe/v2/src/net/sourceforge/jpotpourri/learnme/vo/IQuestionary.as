package net.sourceforge.jpotpourri.learnme.vo {

import mx.collections.ArrayCollection;

import net.sourceforge.jpotpourri.learnme.SuccessRate;
	
[Bindable]
public interface IQuestionary {
	
	function get id(): int;
	function set id(value: int): void;
	
	function get catalog(): IQuestionCatalog;
	
	function get checkedQuestions(): ArrayCollection;
	
	function get date(): Date;
	
	function get successRate(): SuccessRate;
	
}
}