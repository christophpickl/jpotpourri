package net.sourceforge.jpotpourri.learnme.vo {

import mx.collections.ArrayCollection;
	
[Bindable]
internal class AbstractSourceQuestion {
	
	private var _title: String;
	private var _text: String;
	private var _sourceAnswers: ArrayCollection;
	
	
	public function AbstractSourceQuestion(
		title: String,
		text: String,
		sourceAnswers: ArrayCollection
	) {
		this._title = title;
		this._text = text;
		this._sourceAnswers = sourceAnswers;
	}
	
	public function toString(): String {
		return "AbstractSourceQuestion[title="+_title+";text="+_text+";sourceAnswers="+_sourceAnswers+"]";
	}
	
	public function get title():String {
		return this._title;
	}
	
	/** TODO necessary because of binding :( */
	public function set title(value: String): void {
		this._title = value;
	}
	
	public function get text(): String {
		return this._text;
	}
	
	/** TODO necessary because of binding :( */
	public function set text(value: String): void {
		this._text = value;
	}
	
	public function get sourceAnswers(): ArrayCollection {
		return this._sourceAnswers;
	}

}
}
