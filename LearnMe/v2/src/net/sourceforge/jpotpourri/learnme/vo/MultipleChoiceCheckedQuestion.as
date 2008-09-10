package net.sourceforge.jpotpourri.learnme.vo {

import mx.collections.ArrayCollection;
	

[Bindable]
public class MultipleChoiceCheckedQuestion
	extends AbstractCheckedQuestion // MultipleChoiceSourceQuestion
	implements ICheckedQuestion {
	
	public function MultipleChoiceCheckedQuestion(
		sourceQuestion: MultipleChoiceSourceQuestion
	) {
		const checkedAnswers: ArrayCollection = new ArrayCollection();
		for each(var sourceAnswer: MultipleChoiceSourceAnswer in sourceQuestion.sourceAnswers) {
			checkedAnswers.addItem(new MultipleChoiceCheckedAnswer(sourceAnswer));
		}
		super(sourceQuestion.title, sourceQuestion.text, sourceQuestion.sourceAnswers, checkedAnswers);
	}
	

}
}