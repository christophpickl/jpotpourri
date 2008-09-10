package net.sourceforge.jpotpourri.learnme.vo {

import mx.collections.ArrayCollection;
	
[Bindable]
internal class AbstractCheckedQuestion extends AbstractSourceQuestion {
	
	private var _checkedAnswers: ArrayCollection;
	
	public function AbstractCheckedQuestion(
		title: String,
		text: String,
		sourceAnswers: ArrayCollection,
		checkedAnswers: ArrayCollection
	) {
		super(title, text, sourceAnswers);
		this._checkedAnswers = checkedAnswers;
	}

	public function get checkedAnswers(): ArrayCollection {
		return this._checkedAnswers;
	}

	public function set checkedAnswers(value: ArrayCollection): void {
		this._checkedAnswers = value;
	}

	public function get checkedAllCorrect(): Boolean {
		for each(var checkedAnswer: ICheckedAnswer in this.checkedAnswers) {
			if(checkedAnswer.checkedCorrect == false) {
				return false;
			}
		}
		return true;
	}
}
}