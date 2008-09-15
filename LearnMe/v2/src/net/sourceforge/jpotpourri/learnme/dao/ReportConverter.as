package net.sourceforge.jpotpourri.learnme.dao {

import logging.Logger;

import mx.collections.ArrayCollection;
import mx.utils.ObjectUtil;

import net.sourceforge.jpotpourri.learnme.vo.IQuestionCatalog;
import net.sourceforge.jpotpourri.learnme.vo.ISourceQuestion;
import net.sourceforge.jpotpourri.learnme.vo.misc.Report;

internal class ReportConverter {
	
	private static const LOG: Logger = Logger.getLogger("net.sourceforge.jpotpourri.learnme.dao.ReportDao");
	
	public function ReportConverter() {
		// nothing to do
	}
	
	public function convert(catalog: IQuestionCatalog, dbResult: ArrayCollection): ArrayCollection {
		const reports: ArrayCollection = new ArrayCollection();
		
		// be aware of questions, which were not yet exec'ed!!!
		const sqIds: ArrayCollection = new ArrayCollection();
		for each(var sq: ISourceQuestion in catalog.sourceQuestions) {
			sqIds.addItem(sq.id);
		}
		
		
		var lastSourceQuestionId: int = -1;
		var lastCheckedQuestionId: int = -1;
		var report: Report = null;
		var mostRecentDate: Date = null; // TODO how to compare dates in AS3?
		var countCheckedQuestionsTotal: int = -1;
		var countCheckedQuestionsCorrect: int = -1;
		var checkedAnswerCorrect: Boolean = false;
		var checkedDate: Date = null;
		
		var obj: Object = null
		for each(obj in dbResult) {
			trace("for each object: " + ObjectUtil.toString(obj));
			if(lastSourceQuestionId != obj.sq_id) {
				trace("lastSourceQuestionId["+lastSourceQuestionId+"] != obj.sq_id["+obj.sq_id+"]");
				if(countCheckedQuestionsTotal > 0 && checkedAnswerCorrect == true) {
					trace("increasing countCheckedQuestionsCorrect to: " + (countCheckedQuestionsCorrect+1));
					countCheckedQuestionsCorrect++;
				}
				if(report != null) {
					report.mostRecentExecuted = mostRecentDate;
					report.countCheckedQuestionsTotal = countCheckedQuestionsTotal;
					report.countCheckedQuestionsCorrect = countCheckedQuestionsCorrect;
					trace("reports.addItem("+report+");");
					reports.addItem(report);
					sqIds.removeItemAt(sqIds.getItemIndex(report.question.id));
				}
				mostRecentDate = new Date(1970, 1);
				countCheckedQuestionsTotal = 0;
				countCheckedQuestionsCorrect = 0;
				report = new Report(DaoUtil.getQuestionById(catalog, obj.sq_id));
				lastSourceQuestionId = obj.sq_id;
			}
			
			if(lastCheckedQuestionId != obj.cq_id) {
				trace("lastCheckedQuestionId["+lastCheckedQuestionId+"] != obj.cq_id["+obj.cq_id+"]");
				if(countCheckedQuestionsTotal > 0 && checkedAnswerCorrect == true) {
					trace("increasing countCheckedQuestionsCorrect to: " + (countCheckedQuestionsCorrect+1));
					countCheckedQuestionsCorrect++;
				}
				
				trace("increasing countCheckedQuestionsTotal to: " + (countCheckedQuestionsTotal+1));
				countCheckedQuestionsTotal++;
				checkedDate = obj.q_date as Date;
				if(checkedDate.valueOf() > mostRecentDate.valueOf()) {
					mostRecentDate = checkedDate;
				}
				checkedAnswerCorrect = true;
				lastCheckedQuestionId = obj.cq_id;
			}
			trace("checkedAnswerCorrect["+checkedAnswerCorrect+"] && obj.correct["+obj.correct+"] = ["+(checkedAnswerCorrect && Boolean(obj.correct))+"]");
			checkedAnswerCorrect = checkedAnswerCorrect && Boolean(obj.correct);
			trace("=====================================");
		}
		
		if(countCheckedQuestionsTotal > 0 && checkedAnswerCorrect == true) {
			trace("increasing countCheckedQuestionsCorrect to: " + (countCheckedQuestionsCorrect+1));
			countCheckedQuestionsCorrect++;
		}
		checkedDate = obj.q_date as Date;
		if(checkedDate.valueOf() > mostRecentDate.valueOf()) {
			mostRecentDate = checkedDate;
		}
		if(report != null) {
			report.mostRecentExecuted = mostRecentDate;
			report.countCheckedQuestionsTotal = countCheckedQuestionsTotal;
			report.countCheckedQuestionsCorrect = countCheckedQuestionsCorrect;
			trace("reports.addItem("+report+");");
			reports.addItem(report);
			sqIds.removeItemAt(sqIds.getItemIndex(report.question.id));
		}
		
		for each(var sqId: int in sqIds) {
			// insert empty report for this source question
			reports.addItem(Report.newDefault(DaoUtil.getQuestionById(catalog, sqId)));
		}
		
		LOG.finer("Returning ["+reports.length+"] reports.");
		return reports;
	}

}
}