package net.sourceforge.jpotpourri.learnme.vo {

[Bindable]
internal class AbstractCheckedAnswer extends AbstractSourceAnswer {
	
	private var _sourceId: int;
	
	private var _checked: Boolean = false;
	
	public function AbstractCheckedAnswer(
		id: int,
		sourceId: int,
		text: String,
		feedback: String,
		correct: Boolean
	) {
		super(id, text, feedback, correct);
		this._sourceId = sourceId;
	}

	public function get sourceId(): int {
		return this._sourceId;
	}

	public function get checkedCorrect():Boolean {
		return this._checked == this.correct;
	}
	
	public function get checked(): Boolean {
		return this._checked;
	}
	
	public function set checked(checked: Boolean): void {
		this._checked = checked;
	}
}
}