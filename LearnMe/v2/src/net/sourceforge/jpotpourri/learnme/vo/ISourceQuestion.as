package net.sourceforge.jpotpourri.learnme.vo {

import mx.collections.ArrayCollection;
	
[Bindable]
public interface ISourceQuestion {
	
	function get title(): String;
	
	function get text(): String;
	
	function get sourceAnswers(): ArrayCollection;
	
}
}
