package net.sourceforge.jpotpourri.learnme.vo {

[Bindable]
internal class AbstractSourceAnswer {
	
	private var _id: int;
	private var _text: String;
	private var _feedback: String;
	private var _correct: Boolean;
	
	public function AbstractSourceAnswer(
		id: int,
		text: String,
		feedback: String,
		correct: Boolean
	) {
		this._id = id;
		this._text = text;
		this._feedback = feedback;
		this._correct = correct;
	}

	
	public function toString(): String {
		return "AbstractSourceAnswer[id="+_id+";text="+_text+";feedback="+_feedback+";correct="+_correct+";]";
	}
	
	public function get id(): int {
		return this._id;
	}
	
	public function set id(value: int): void {
		this._id = id;
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