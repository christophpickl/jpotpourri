package net.sourceforge.jpotpourri.learnme.vo {

import mx.collections.ArrayCollection;

[Bindable]
public class Questionairy implements IQuestionary {
	
	private var _checkedQuestions: ArrayCollection;
	
	public function Questionairy(checkedQuestions: ArrayCollection) {
		this._checkedQuestions = checkedQuestions;
	}

	public function get checkedQuestions():ArrayCollection {
		return this._checkedQuestions;
	}

	/** TODO necessary because of binding :( */
	public function set checkedQuestions(value: ArrayCollection): void{
		this._checkedQuestions = value;
	}
	
}
}