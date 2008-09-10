package classes {
	import mx.collections.ArrayCollection;
	

[Bindable]
public class QuestionSet {
	
	/** ArrayCollection<ITestQuestion> */
	public var questions:ArrayCollection;
	
	
	public function QuestionSet(questions:ArrayCollection) {
		this.questions = questions;
	}

}
}