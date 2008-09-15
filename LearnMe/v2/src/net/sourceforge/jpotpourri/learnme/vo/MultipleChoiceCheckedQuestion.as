package net.sourceforge.jpotpourri.learnme.vo {

import mx.collections.ArrayCollection;
	

[Bindable]
public class MultipleChoiceCheckedQuestion
	extends AbstractCheckedQuestion // MultipleChoiceSourceQuestion
	implements ICheckedQuestion {
	
	public function MultipleChoiceCheckedQuestion(
		id: int,
		sourceId: int,
		title: String,
		text: String,
		sourceAnswers: ArrayCollection,
		checkedAnswers: ArrayCollection 
	) {
		super(id, sourceId, title, text, sourceAnswers, checkedAnswers);
	}
	
	public static function newDefault(sourceQuestion: MultipleChoiceSourceQuestion): MultipleChoiceCheckedQuestion {
		
		const checkedAnswers: ArrayCollection = new ArrayCollection();
		for each(var sourceAnswer: MultipleChoiceSourceAnswer in sourceQuestion.sourceAnswers) {
			checkedAnswers.addItem(new MultipleChoiceCheckedAnswer(-1, sourceAnswer));
		}
		
		return new MultipleChoiceCheckedQuestion(-1, sourceQuestion.id, sourceQuestion.title, sourceQuestion.text, sourceQuestion.sourceAnswers, checkedAnswers);
	}

}
}