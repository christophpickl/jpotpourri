package net.sourceforge.jpotpourri.learnme.vo {

[Bindable]
internal class AbstractSourceAnswer {
	
	private var _text: String;
	private var _feedback: String;
	private var _correct: Boolean;
	
	public function AbstractSourceAnswer(
		text: String,
		feedback: String,
		correct: Boolean
	) {
		this._text = text;
		this._feedback = feedback;
		this._correct = correct;
	}

	public function get text():String {
		return this._text;
	}

	/** TODO necessary because of binding :( */
	public function set text(value: String): void {
		this._text = value;
	}
	
	public function get feedback():String {
		return this._feedback;
	}
	
	public function get correct():Boolean {
		return this._correct;
	}
	
}
}