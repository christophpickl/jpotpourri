package net.sourceforge.jpotpourri.learnme.vo {

[Bindable]
public class MultipleChoiceSourceAnswer extends AbstractSourceAnswer implements ISourceAnswer {
	
	public function MultipleChoiceSourceAnswer(id: int, text: String, feedback: String, correct: Boolean) {
		super(id, text, feedback, correct);
	}
	
}
}