package net.sourceforge.jpotpourri.learnme.vo.misc {

import logging.Logger;

import mx.collections.ArrayCollection;
import mx.collections.Sort;
import mx.collections.SortField;

import net.sourceforge.jpotpourri.learnme.vo.ISourceQuestion;

public class Report {
	
	private static const LOG: Logger = Logger.getLogger("net.sourceforge.jpotpourri.learnme.vo.misc.Report");
	
	public var question: ISourceQuestion;
	
	public var countCheckedQuestionsTotal: int;
	public var countCheckedQuestionsCorrect: int;
	
	public var mostRecentExecuted: Date;
	
	private var _weight: int = -1;
	
	public function Report(pQuestion: ISourceQuestion) {
		this.question = pQuestion;
	}
	
	/**
	 * @return number between 0-100
	 */
	public function get weight(): int {
		if(_weight == -1) {
			// compute weight
			if(countCheckedQuestionsTotal == 0) {
				_weight = 100;
			} else {
				const percentCorrect: int = Math.round(countCheckedQuestionsCorrect / countCheckedQuestionsTotal * 100);
				const percentWrong: int = 100 - percentCorrect;
				const daysAgo: int = (new Date().time - mostRecentExecuted.time) / (1000*60*60*24);
				
				var tmpWeight: int = 0;
				tmpWeight += weightByPercentWrong(percentWrong);
				tmpWeight += weightByDaysAgo(daysAgo);
				tmpWeight += weightByTotalCount(countCheckedQuestionsTotal);
				
				if(tmpWeight > 100) {
					_weight = 100;
				} else {
					_weight = tmpWeight;
				}
				LOG.finest(this+" ... daysAgo["+daysAgo+"]; percentWrong["+percentWrong+"]; weight ==> ["+_weight+"]");
			}
		}
		return _weight;
	}
	
	private static function weightByTotalCount(totalCount: int): int {
		if(totalCount > 0 && totalCount <= 2) {
			return 40;
		} else if(totalCount > 2 && totalCount <= 5) {
			return 35;
		} else if(totalCount > 5 && totalCount <= 10) {
			return 30;
		} else if(totalCount > 10 && totalCount <= 20) {
			return 20;
		} else if(totalCount > 20 && totalCount <= 40) {
			return 10;
		} else if(totalCount > 40 && totalCount <= 70) {
			return 5;
		} else {
			return 0;
		}
	}
	private static function weightByDaysAgo(daysAgo: int): int {
		if(daysAgo == 0) {
			return 0;
		} else if(daysAgo == 1) {
			return 5;
		} else if(daysAgo >= 2 && daysAgo <= 5) {
			return 10;
		} else if(daysAgo >= 6 && daysAgo <= 11) {
			return 20;
		} else if(daysAgo >= 12 && daysAgo <= 22) {
			return 40;
		} else if(daysAgo >= 23 && daysAgo <= 31) {
			return 60;
		} else {
			return 80;
		}
	}
	private static function weightByPercentWrong(percentWrong: int): int {
		if(percentWrong >= 0 && percentWrong <= 5) {
			return 0;
		} else if(percentWrong >= 6 && percentWrong <= 15) {
			return 10;
		} else if(percentWrong >= 16 && percentWrong <= 25) {
			return 20;
		} else if(percentWrong >= 26 && percentWrong <= 45) {
			return 30;
		} else if(percentWrong >= 46 && percentWrong <= 65) {
			return 40;
		} else if(percentWrong >= 66 && percentWrong <= 75) {
			return 65;
		} else if(percentWrong >= 76 && percentWrong <= 85) {
			return 70;
		} else if(percentWrong >= 86 && percentWrong <= 95) {
			return 75;
		} else if(percentWrong >= 96 && percentWrong <= 100) {
			return 80;
		}
		throw new Error("ups :) ["+percentWrong+"]");
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


	public static function sortReports(reports: ArrayCollection): void {
		LOG.fine("sorting reports");
		var sortField: SortField = new SortField();
		sortField.name = "weight";
		sortField.numeric = true;
		sortField.descending = true;
		
		var sort: Sort = new Sort();
		sort.fields = [sortField]; // [sortField1, sortField2, ...]
		reports.sort = sort;
		reports.refresh();
		trace("sorting reports");
	}
}
}