package classes {

import mx.controls.List;

public interface ITestQuestion extends IGenericQuestion {
	
	function get answerRenderer():Class;
	
	function hasUserCorrect():Boolean;
	
}
}