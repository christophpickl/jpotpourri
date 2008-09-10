package net.sourceforge.jpotpourri.learnme.vo {

import mx.collections.ArrayCollection;

[Bindable]
public class MultipleChoiceSourceQuestion extends AbstractSourceQuestion implements ISourceQuestion {
	
	public function MultipleChoiceSourceQuestion(title: String, text: String, sourceAnswers: ArrayCollection) {
		super(title, text, sourceAnswers);
	}
	
}
}