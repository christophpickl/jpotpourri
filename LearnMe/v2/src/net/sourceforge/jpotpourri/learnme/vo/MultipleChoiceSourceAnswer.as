package net.sourceforge.jpotpourri.learnme.vo {

[Bindable]
public class MultipleChoiceSourceAnswer extends AbstractSourceAnswer implements ISourceAnswer {
	
	public function MultipleChoiceSourceAnswer(text: String, feedback: String, correct: Boolean) {
		super(text, feedback, correct);
	}
	
}
}