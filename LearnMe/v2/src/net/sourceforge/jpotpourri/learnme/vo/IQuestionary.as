package net.sourceforge.jpotpourri.learnme.vo {

import mx.collections.ArrayCollection;
	
[Bindable]
public interface IQuestionary {
	
	function get checkedQuestions(): ArrayCollection;
	
}
}