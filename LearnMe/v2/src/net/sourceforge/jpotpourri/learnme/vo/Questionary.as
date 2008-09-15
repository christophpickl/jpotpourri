package net.sourceforge.jpotpourri.learnme.vo {

import mx.collections.ArrayCollection;

import net.sourceforge.jpotpourri.learnme.SuccessRate;

[Bindable]
public class Questionary implements IQuestionary {
	
	private var _id: int;
	
	private var _checkedQuestions: ArrayCollection;
	
	private var _date: Date;
	
	public var catalog: IQuestionCatalog;
	
	public function Questionary(id: int, catalog: IQuestionCatalog, date: Date, checkedQuestions: ArrayCollection) {
		this._id = id;
		this.catalog = catalog;
		this._date = date;
		this._checkedQuestions = checkedQuestions;
	}
	
	
	public function get id(): int {
		return this._id;
	}

	public function set id(value: int): void {
		this._id = value;
	}
	
	public function get checkedQuestions():ArrayCollection {
		return this._checkedQuestions;
	}

	/** TODO necessary because of binding :( */
	public function set checkedQuestions(value: ArrayCollection): void{
		this._checkedQuestions = value;
	}
	
	public function get date(): Date {
		return this._date;
	}
	
	public function get successRate(): SuccessRate {
		const totalQuestions: int = this.checkedQuestions.length;
		var questionsCorrect: int = 0;
		for each(var question: ICheckedQuestion in this.checkedQuestions) {
			if(question.checkedAllCorrect == true) {
				questionsCorrect++;
			}
		}
		// LOG.finest("questionary.id="+questionary.id+": totalQuestions="+totalQuestions+"; questionsCorrect="+questionsCorrect);
		var percent: Number = Math.round(100 * (questionsCorrect / totalQuestions));
		
		return new SuccessRate(percent, totalQuestions, questionsCorrect);
	}
}
}