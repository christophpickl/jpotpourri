package net.sourceforge.jpotpourri.learnme.vo {

import mx.collections.ArrayCollection;
	

public interface IQuestionCatalog {
	
	function get id(): int;
	
	function set id(value: int): void;
	
	function get title(): String;
	
	function set title(value: String): void;
	
	function get sourceQuestions(): ArrayCollection;
	
}
}