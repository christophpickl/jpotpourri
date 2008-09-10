package net.sourceforge.jpotpourri.learnme.vo {

import mx.collections.ArrayCollection;


public class QuestionCatalog implements IQuestionCatalog {
	
	private var _title: String;
	
	private var _sourceQuestions: ArrayCollection;
	
	
	public function QuestionCatalog(title: String, sourceQuestions: ArrayCollection) {
		this._title = title;
		this._sourceQuestions = sourceQuestions;
	}

	
	public function get title(): String {
		return this._title;
	}
	
	public function get sourceQuestions(): ArrayCollection {
		return this._sourceQuestions;
	}
	
}
}