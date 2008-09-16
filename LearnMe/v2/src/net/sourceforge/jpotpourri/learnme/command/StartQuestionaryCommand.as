package net.sourceforge.jpotpourri.learnme.command {

import logging.Logger;

import mx.collections.ArrayCollection;
import mx.core.Application;

import net.sourceforge.jpotpourri.learnme.dao.DaoLocator;
import net.sourceforge.jpotpourri.learnme.event.StartQuestionaryEvent;
import net.sourceforge.jpotpourri.learnme.model.ModelLocator;
import net.sourceforge.jpotpourri.learnme.vo.ICheckedQuestion;
import net.sourceforge.jpotpourri.learnme.vo.IQuestionCatalog;
import net.sourceforge.jpotpourri.learnme.vo.IQuestionary;
import net.sourceforge.jpotpourri.learnme.vo.MultipleChoiceCheckedQuestion;
import net.sourceforge.jpotpourri.learnme.vo.MultipleChoiceSourceQuestion;
import net.sourceforge.jpotpourri.learnme.vo.Questionary;
import net.sourceforge.jpotpourri.learnme.vo.misc.Report;
	

public class StartQuestionaryCommand {

	private static const LOG: Logger = Logger.getLogger("net.sourceforge.jpotpourri.learnme.command.StartQuestionaryCommand");

	private static const MAX_QUESTIONS: uint = 20;

	private var _catalog: IQuestionCatalog;

	public function StartQuestionaryCommand() {
	}
	
	public function execute(event: StartQuestionaryEvent): void {
		_catalog = event.questionCatalog;
		LOG.info("StartQuestionaryCommand; catalog: " + _catalog);
		
		if(event.questionsSelection == StartQuestionaryEvent.QUESTIONS_ALL) {
			
			const checkedQuestions: ArrayCollection = new ArrayCollection();
			for (var i: int = 0; i < _catalog.sourceQuestions.length; i++) {
				var question: MultipleChoiceSourceQuestion = MultipleChoiceSourceQuestion(_catalog.sourceQuestions.getItemAt(i));
				checkedQuestions.addItem(MultipleChoiceCheckedQuestion.newDefault(question));
			}
			this.doStartQuestionary(checkedQuestions);
			
		} else if(event.questionsSelection == StartQuestionaryEvent.QUESTIONS_LIMITED_BY_WEIGHT) {
			DaoLocator.instance.reportDao.selectReports(_catalog, onReportsFetched);
		} else {
			throw new Error("unhandled event.questionsSelection ["+event.questionsSelection+"]!");
		}
		
	}
	
	private function onReportsFetched(reports: ArrayCollection): void {
		const checkedQuestions: ArrayCollection = new ArrayCollection();
		
		if(reports.length != _catalog.sourceQuestions.length) {
			throw new Error("assertion failed: reports.length["+reports.length+"] != _catalog.sourceQuestions.length["+_catalog.sourceQuestions.length+"]");
		}
		Report.sortReports(reports);
		// FIXME use reports to get proper questions
		for (var i: int = 0; i < Math.min(_catalog.sourceQuestions.length, MAX_QUESTIONS); i++) {
			
			var report: Report = Report(reports.getItemAt(i));
			var question: MultipleChoiceSourceQuestion = MultipleChoiceSourceQuestion(report.question);
			checkedQuestions.addItem(MultipleChoiceCheckedQuestion.newDefault(question));
		}
		
		this.doStartQuestionary(checkedQuestions);
	}
	
	private function doStartQuestionary(checkedQuestions: ArrayCollection): void {
		const questionary: IQuestionary = new Questionary(-1, _catalog, new Date(), checkedQuestions);
		
		ModelLocator.instance.questionary = questionary;
		ModelLocator.instance.currentQuestion = ICheckedQuestion(questionary.checkedQuestions.getItemAt(0));
		
		Application.application.currentState = 'Questionairy';
	}

}
}