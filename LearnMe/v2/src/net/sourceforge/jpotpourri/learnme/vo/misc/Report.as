package net.sourceforge.jpotpourri.learnme.vo.misc {

import net.sourceforge.jpotpourri.learnme.vo.ISourceQuestion;
	


public class Report {
	
	public var question: ISourceQuestion;
	// count of total executions
	// success rate: percent, and absolute
	// last time, question was executed
	
	public function Report(pQuestion: ISourceQuestion) {
		this.question = pQuestion;
	}

}
}