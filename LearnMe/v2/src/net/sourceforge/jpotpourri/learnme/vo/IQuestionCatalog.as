package net.sourceforge.jpotpourri.learnme.vo {

import mx.collections.ArrayCollection;
	

public interface IQuestionCatalog {
	
	function get title(): String;
	
	function get sourceQuestions(): ArrayCollection;
	
}
}