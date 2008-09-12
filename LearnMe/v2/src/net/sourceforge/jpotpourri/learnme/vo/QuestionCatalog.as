package net.sourceforge.jpotpourri.learnme.vo {

import mx.collections.ArrayCollection;


public class QuestionCatalog implements IQuestionCatalog {
	
	private var _id: int;
	
	private var _title: String;
	
	private var _sourceQuestions: ArrayCollection;
	
	
	public function QuestionCatalog(id: int, title: String, sourceQuestions: ArrayCollection) {
		this._id = id;
		this._title = title;
		this._sourceQuestions = sourceQuestions;
	}

	public function toString(): String {
		return "QuestionCatalog[title="+_title+";sourceQuestions="+_sourceQuestions+"]";
	}
	
	
	public function get id(): int {
		return this._id;
	}

	public  function set id(value: int): void {
		this._id = value;
	}
	
	public function get title(): String {
		return this._title;
	}
	
	public function get sourceQuestions(): ArrayCollection {
		return this._sourceQuestions;
	}
	
}
}