package net.sourceforge.jpotpourri.learnme.vo {

[Bindable]
internal class AbstractCheckedAnswer extends AbstractSourceAnswer {
	
	private var _checked: Boolean;
	
	public function AbstractCheckedAnswer(
		id: int,
		text: String,
		feedback: String,
		correct: Boolean
	) {
		super(id, text, feedback, correct);
	}

	public function get checkedCorrect():Boolean {
		return this._checked == this.correct;
	}
	
	public function get checked(): Boolean {
		return this._checked;
	}
	
	/** necessary because of binding */
	public function set checked(checked: Boolean): void {
		this._checked = checked;
	}
}
}