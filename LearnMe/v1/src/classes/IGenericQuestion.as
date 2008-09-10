package classes {

import mx.collections.ArrayCollection;
	
public interface IGenericQuestion {
	
	function get answerClass():Class;
	
	function get title():String;
	function set title(title:String):void;
	
	function get type():String;
	
	function get text():String;
	function set text(text:String):void;
	
	function get answers():ArrayCollection;
	function set answers(answers:ArrayCollection):void;
	
}
}