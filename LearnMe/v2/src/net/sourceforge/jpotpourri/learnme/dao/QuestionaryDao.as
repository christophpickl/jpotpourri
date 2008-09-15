package net.sourceforge.jpotpourri.learnme.dao {
	import logging.Logger;
	
	import mx.collections.ArrayCollection;
	
	import net.sourceforge.jpotpourri.learnme.vo.ICheckedAnswer;
	import net.sourceforge.jpotpourri.learnme.vo.ICheckedQuestion;
	import net.sourceforge.jpotpourri.learnme.vo.IQuestionCatalog;
	import net.sourceforge.jpotpourri.learnme.vo.IQuestionary;
	import net.sourceforge.jpotpourri.learnme.vo.ISourceAnswer;
	import net.sourceforge.jpotpourri.learnme.vo.ISourceQuestion;
	import net.sourceforge.jpotpourri.learnme.vo.MultipleChoiceCheckedAnswer;
	import net.sourceforge.jpotpourri.learnme.vo.MultipleChoiceCheckedQuestion;
	import net.sourceforge.jpotpourri.learnme.vo.MultipleChoiceSourceAnswer;
	import net.sourceforge.jpotpourri.learnme.vo.MultipleChoiceSourceQuestion;
	import net.sourceforge.jpotpourri.learnme.vo.Questionary;
	


internal class QuestionaryDao extends AbstractDao implements IQuestionaryDao {
	
	private static const LOG:Logger = Logger.getLogger("net.sourceforge.jpotpourri.learnme.dao.QuestionaryDao");
	
	private static const SQL_CREATE_QUESTIONARY: String = 
		"CREATE TABLE IF NOT EXISTS questionary (" +
		  "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
		  "id_catalog INTEGER, " +
		  "date DATETIME" +
		")";
	private static const SQL_CREATE_C_QUESTION: String = 
		"CREATE TABLE IF NOT EXISTS c_question (" +
		  "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
		  "id_questionary INTEGER, " +
		  "id_s_question INTEGER " +
		")";
	private static const SQL_CREATE_C_ANSWER: String = 
		"CREATE TABLE IF NOT EXISTS c_answer (" +
		  "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
		  "id_c_question INTEGER, " +
		  "id_s_answer INTEGER, " +
		  "checked BOOLEAN " +
		")";
	
	
	private static const SQL_INSERT_QUESTIONARY: String =
		"INSERT INTO questionary (id_catalog, date) VALUES (:catalogId, :questionaryDate)";
	private static const SQL_INSERT_C_QUESTION: String =
		"INSERT INTO c_question (id_questionary, id_s_question) VALUES (:questionaryId, :sourceQuestionId)";
	private static const SQL_INSERT_C_ANSWER: String =
		"INSERT INTO c_answer (id_c_question, id_s_answer, checked) VALUES (:checkedQuestionId, :sourceAnswerId, :checked)";
		
	
	private static const SQL_SELECT_ALL: String = 
		"SELECT " +
		  "q.id AS  q_id, q.date AS q_date, " +
		  "cq.id AS cq_id, cq.id_s_question AS cq_id_sq, " +
		  "ca.id AS ca_id, ca.id_s_answer AS ca_id_sa, ca.checked AS ca_checked " +
		"FROM questionary AS q " +
		  "JOIN c_question AS cq ON q.id = cq.id_questionary " +
		  "JOIN c_answer AS ca ON cq.id = ca.id_c_question " + 
		"WHERE " +
		  "q.id_catalog = :catalogId";
	
		
	private var _fnResult: Function;
	private var _fnResultCatalog: IQuestionCatalog;
	
	public function QuestionaryDao() {
		execSql(SQL_CREATE_QUESTIONARY);
		execSql(SQL_CREATE_C_QUESTION);
		execSql(SQL_CREATE_C_ANSWER);
	}

	public function insertQuestionary(questionary: IQuestionary): void {
		LOG.info("inserting questionary: " + questionary);
		
		var params: Array = new Array();
		params[":catalogId"] = questionary.catalog.id;
		params[":questionaryDate"] = questionary.date;
		execSql(SQL_INSERT_QUESTIONARY, null, params);
		
		var lastQuestionaryId: int = this.lastInsertId;
		LOG.finer("Setting id for inserted questionary to ["+lastQuestionaryId+"].");
		questionary.id = lastQuestionaryId;
		
		for each(var question: ICheckedQuestion in questionary.checkedQuestions) {
			LOG.finer("inserting checked question: " + question);
			
			params  = new Array();
			params[":questionaryId"] = questionary.id;
			params[":sourceQuestionId"] = question.sourceId;
			
			execSql(SQL_INSERT_C_QUESTION, null, params);
			var lastQuestionId: int = this.lastInsertId;
			question.id = lastQuestionId;
			
			for each(var answer: ICheckedAnswer in question.checkedAnswers) {
				params  = new Array();
				params[":checkedQuestionId"] = lastQuestionId;
				params[":sourceAnswerId"] = answer.sourceId;
				params[":checked"] = answer.checked;
				
				execSql(SQL_INSERT_C_ANSWER, null, params);
				var lastAnswerId: int = this.lastInsertId;
				answer.id = lastAnswerId;
			}
		}
	}
	
	public function selectQuestionariesForCatalog(catalog: IQuestionCatalog, fnResult: Function): void {
		this._fnResult = fnResult;
		this._fnResultCatalog= catalog;
		var params: Array = new Array();
		params[":catalogId"] = catalog.id;
		execSql(SQL_SELECT_ALL, onSelectQuestionariesResult, params);
		
	}
	private function onSelectQuestionariesResult(result: ArrayCollection): void {
		LOG.finer("onSelectCatalogsResult for "+result.length+" items.");
		
		this._fnResult(convertObjectsToQuestionaries(this._fnResultCatalog, result));
		this._fnResult = null;
		this._fnResultCatalog= null;
	}
	
	/** returns list of Questionaries */
	private static function convertObjectsToQuestionaries(catalog: IQuestionCatalog, data: ArrayCollection): ArrayCollection {
		const result: ArrayCollection = new ArrayCollection();
		
		/*
		obj.x
			q_id ... questionary id
			q_date ... questionary date
			
			cq_id ... checked question id
			cq_id_sq ... checked question, source id
			
			ca_id ... checked answer id
			ca_id_sa ... checked answer, source id
			ca_checked ... checked answer, checked
		*/
		
		var lastQuestionaryId: int = -1;
		var questionary: IQuestionary = null;
		var questions: ArrayCollection = null; // checked questions
		
		var lastQuestionId: int = -1;
		var question: ICheckedQuestion =  null;
		var answers: ArrayCollection = null;
		var sourceQuestion: MultipleChoiceSourceQuestion = null;
		
		var lastAnswerId: int = -1;
		var answer: ICheckedAnswer =  null;
		
		for each(var obj: Object in data) {
			
			if(lastQuestionaryId != obj.q_id) { // new questionary
				if(questionary != null) { // store back recent finished questionary
					questions.addItem(question);
					question = null;
					result.addItem(questionary);
				}
				questions = new ArrayCollection();
				questionary = new Questionary(obj.q_id, catalog, obj.q_date as Date, questions); 
				lastQuestionaryId = obj.q_id;
			}
			
			if(lastQuestionId != obj.cq_id) { // new checked question
				if(question != null) { // store back recent finished questionary
					answers.addItem(answer);
					answer = null;
					questions.addItem(question);
				}
				sourceQuestion = DaoUtil.getQuestionById(catalog, obj.cq_id_sq);
				answers = new ArrayCollection();
				question = new MultipleChoiceCheckedQuestion(obj.cq_id, sourceQuestion.id, sourceQuestion.title, sourceQuestion.text, sourceQuestion.sourceAnswers, answers);
				lastQuestionId = obj.cq_id;
			}
			
			if(lastAnswerId != obj.ca_id) { // new checked question
				if(answer != null) { // store back recent finished questionary
					answers.addItem(answer);
				}
				var sourceAnswer: MultipleChoiceSourceAnswer = DaoUtil.getAnswerById(sourceQuestion, obj.ca_id_sa);
				answer = new MultipleChoiceCheckedAnswer(obj.ca_id, sourceAnswer);
				answer.checked = obj.ca_checked;
				
				lastAnswerId = obj.ca_id;
			}
		}
		
		if(answer != null) {
			answers.addItem(answer);
		}
		if(question != null) {
			questions.addItem(question);
		}
		if(questionary != null) {
			result.addItem(questionary);
		}
		
		return result;
	}
	
}
}