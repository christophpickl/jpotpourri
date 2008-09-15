package net.sourceforge.jpotpourri.learnme.vo {

import mx.collections.ArrayCollection;

[Bindable]
public interface ICheckedQuestion extends ISourceQuestion {
	
	function get sourceId(): int;
	
	function get checkedAnswers(): ArrayCollection;
	
	function get checkedAllCorrect(): Boolean;
	
}
}