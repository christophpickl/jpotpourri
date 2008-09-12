package net.sourceforge.jpotpourri.learnme.vo {

[Bindable]
public class MultipleChoiceCheckedAnswer extends AbstractCheckedAnswer implements ICheckedAnswer {
	
	public function MultipleChoiceCheckedAnswer(
		sourceAnswer: MultipleChoiceSourceAnswer
	) {
		super(sourceAnswer.id, sourceAnswer.text, sourceAnswer.feedback, sourceAnswer.correct);
	}
	
	
}
}