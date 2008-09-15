package net.sourceforge.jpotpourri.learnme.vo.misc {

import net.sourceforge.jpotpourri.learnme.vo.ISourceQuestion;

public class Report {
	
	public var question: ISourceQuestion;
	
	public var countCheckedQuestionsTotal: int;
	public var countCheckedQuestionsCorrect: int;
	
	public var mostRecentExecuted: Date;
	
	public function Report(pQuestion: ISourceQuestion) {
		this.question = pQuestion;
	}
	
	public static function newDefault(pQuestion: ISourceQuestion): Report {
		const report: Report = new Report(pQuestion);
		report.countCheckedQuestionsTotal = 0;
		report.countCheckedQuestionsCorrect = 0;
		report.mostRecentExecuted = new Date(1970, 1);
		return report;
	}
	
	public function toString(): String {
		return "Report[question.id="+question.id+";total="+countCheckedQuestionsTotal+";correct="+countCheckedQuestionsCorrect+";mostRecentExecuted="+mostRecentExecuted+"]";
	}

}
}