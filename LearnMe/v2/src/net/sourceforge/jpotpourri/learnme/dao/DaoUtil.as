package net.sourceforge.jpotpourri.learnme.dao {

import net.sourceforge.jpotpourri.learnme.vo.IQuestionCatalog;
import net.sourceforge.jpotpourri.learnme.vo.ISourceAnswer;
import net.sourceforge.jpotpourri.learnme.vo.ISourceQuestion;
import net.sourceforge.jpotpourri.learnme.vo.MultipleChoiceSourceAnswer;
import net.sourceforge.jpotpourri.learnme.vo.MultipleChoiceSourceQuestion;
	

internal class DaoUtil {
	
	public function DaoUtil() {
	}

	public static function getQuestionById(catalog: IQuestionCatalog, sourceQuestionId: int): MultipleChoiceSourceQuestion {
		for each(var sourceQuestion: ISourceQuestion in catalog.sourceQuestions) {
			if(sourceQuestion.id == sourceQuestionId) {
				return MultipleChoiceSourceQuestion(sourceQuestion);
			}
		}
		throw new Error("Could not find source question by id ["+sourceQuestionId+"] in catalog: " + catalog);
	}
	
	public static function getAnswerById(question: MultipleChoiceSourceQuestion, sourceAnswerId: int): MultipleChoiceSourceAnswer {
		for each(var sourceAnswer: ISourceAnswer in question.sourceAnswers) {
			if(sourceAnswer.id == sourceAnswerId) {
				return MultipleChoiceSourceAnswer(sourceAnswer);
			}
		}
		throw new Error("Could not find source answer by id ["+sourceAnswerId+"] in question: " + question);
	}
	
}
}