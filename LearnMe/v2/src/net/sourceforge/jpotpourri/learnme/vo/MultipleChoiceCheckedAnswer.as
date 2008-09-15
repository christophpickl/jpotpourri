package net.sourceforge.jpotpourri.learnme.vo {

[Bindable]
public class MultipleChoiceCheckedAnswer extends AbstractCheckedAnswer implements ICheckedAnswer {
	
	public function MultipleChoiceCheckedAnswer(
		id: int,
		sourceAnswer: MultipleChoiceSourceAnswer
	) {
		super(id, sourceAnswer.id, sourceAnswer.text, sourceAnswer.feedback, sourceAnswer.correct);
	}
	
	
}
}