package net.sourceforge.jpotpourri.learnme.dao {

import logging.Logger;

import mx.collections.ArrayCollection;
import mx.utils.ObjectUtil;

import net.sourceforge.jpotpourri.learnme.vo.IQuestionCatalog;
import net.sourceforge.jpotpourri.learnme.vo.ISourceAnswer;
import net.sourceforge.jpotpourri.learnme.vo.ISourceQuestion;
import net.sourceforge.jpotpourri.learnme.vo.MultipleChoiceSourceAnswer;
import net.sourceforge.jpotpourri.learnme.vo.MultipleChoiceSourceQuestion;
import net.sourceforge.jpotpourri.learnme.vo.QuestionCatalog;
	

internal class CatalogDao extends AbstractDao implements ICatalogDao {
	
	private static const LOG:Logger = Logger.getLogger("net.sourceforge.jpotpourri.learnme.dao.CatalogDao");
	
	private static const SQL_CREATE_CATALOG: String = 
		"CREATE TABLE IF NOT EXISTS catalog (" +
		  "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
		  "title CHAR(255)" +
		")";
	private static const SQL_CREATE_S_QUESTION: String = 
		"CREATE TABLE IF NOT EXISTS s_question (" +
		  "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
		  "id_catalog INTEGER, " +
		  "title CHAR(255), " +
		  "text TEXT" +
		")";
	private static const SQL_CREATE_S_ANSWER: String = 
		"CREATE TABLE IF NOT EXISTS s_answer (" +
		  "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
		  "id_s_question INTEGER, " +
		  "text TEXT," +
		  "feedback TEXT, " +
		  "correct BOOLEAN " +
		")";
	
	// SELECT c.id AS c_id, c.title AS c_title, q.id AS q_id, q.title AS q_title, q.text AS q_text, a.id AS a_id, a.text AS a_text, a.feedback AS a_feedback, a.correct AS a_correct FROM catalog AS c JOIN s_question AS q ON c.id = q.id_catalog JOIN s_answer AS a   ON q.id = a.id_s_question 
	private static const SQL_SELECT_ALL: String = 
		"SELECT " +
		  "c.id AS c_id, c.title AS c_title, " +
		  "q.id AS q_id, q.title AS q_title, q.text AS q_text, " +
		  "a.id AS a_id, a.text AS a_text, a.feedback AS a_feedback, a.correct AS a_correct " +
		"FROM catalog AS c " +
		  "JOIN s_question AS q ON c.id = q.id_catalog " +
		  "JOIN s_answer AS a   ON q.id = a.id_s_question ";
	
	private static const SQL_INSERT_CATALOG: String =
		"INSERT INTO catalog (title) VALUES (:catalogTitle)";
	private static const SQL_INSERT_QUESITON: String =
		"INSERT INTO s_question (id_catalog, title, text) VALUES (:catalogId, :questionTitle, :questionText)";
	private static const SQL_INSERT_ANSWER: String =
		"INSERT INTO s_answer (id_s_question, text, feedback, correct) VALUES (:questionId, :answerText, :answerFeedback, :answerCorrect)";
	
	private var _fnResult: Function;
	
	
	public function CatalogDao() {
		execSql(SQL_CREATE_CATALOG);
		execSql(SQL_CREATE_S_QUESTION);
		execSql(SQL_CREATE_S_ANSWER);
	}
	
	public function insertCatalog(catalog: IQuestionCatalog): void {
		LOG.info("inserting question catalog: " + catalog);
		
		var params: Array = new Array();
		params[":catalogTitle"] = catalog.title;
		execSql(SQL_INSERT_CATALOG, null, params);
		
		var lastCatalogId: int = this.lastInsertId;
		LOG.finer("Setting id for inserted catalog to ["+lastCatalogId+"].");
		catalog.id = lastCatalogId;
		
		for each(var question: ISourceQuestion in catalog.sourceQuestions) {
			LOG.finer("inserting question: " + question);
			params = new Array();
			params[":catalogId"] = lastCatalogId;
			params[":questionTitle"] = question.title;
			params[":questionText"] = question.text;
			execSql(SQL_INSERT_QUESITON, null, params);
					
			var lastQuestionId: int = this.lastInsertId;
			
			for each(var answer: ISourceAnswer in question.sourceAnswers) {
				params = new Array();
				params[":questionId"] = lastQuestionId;
				params[":answerText"] = answer.text;
				params[":answerFeedback"] = answer.feedback;
				params[":answerCorrect"] = answer.correct;
				execSql(SQL_INSERT_ANSWER, null, params);
			}
		}
	}
	
	
	public function selectCatalogs(fnResult: Function): void {
		_fnResult = fnResult;
		execSql(SQL_SELECT_ALL, this.onSelectCatalogsResult);
	}
	
	public function selectCatalogsByTitle(title: String, fnResult: Function): void {
		_fnResult = fnResult;
		execSql("SELECT id FROM catalog WHERE title='"+title+"'", onSelectCatalogsResult);
	}
	
	private function onSelectCatalogsResult(result: ArrayCollection): void {
		LOG.finer("onSelectCatalogsResult for "+result.length+" items.");
		
		_fnResult(convertObjectsToCatalogs(result));
	}

	private static function convertObjectsToCatalogs(data: ArrayCollection): ArrayCollection {
		const result: ArrayCollection = new ArrayCollection();
		
		var lastCatalogId: int = -1;
		var lastQuestionId: int = -1;
		var catalog: IQuestionCatalog = null;
		var questions: ArrayCollection = null;
		var question: ISourceQuestion = null;
		var answers: ArrayCollection = null;
		var answer: ISourceAnswer = null;
		
		for each(var obj: Object in data) {
			// LOG.finest("processing object: " + ObjectUtil.toString(obj));
			if(lastCatalogId != obj.c_id) { // new catalog
				if(catalog != null) { // store back recent finished catalog
					questions.addItem(question);
					question = null; // disable adding question to next catalog, but use it right here!
					result.addItem(catalog);
				}
				questions = new ArrayCollection();
				catalog = new QuestionCatalog(obj.c_id, obj.c_title, questions);
				lastCatalogId = obj.c_id;
			}
			
			if(lastQuestionId != obj.q_id) { // new question
				if(question != null) { // store back recent finished question
					questions.addItem(question);
				}
				answers = new ArrayCollection();
				question = new MultipleChoiceSourceQuestion(obj.q_id, obj.q_title, obj.q_text, answers);
				lastQuestionId = obj.q_id;
			}
			
			answer = new MultipleChoiceSourceAnswer(obj.a_id, obj.a_text, obj.a_feedback, obj.a_correct);
			answers.addItem(answer);
		}
		
		if(question != null) {
			questions.addItem(question);
		}
		if(catalog != null) {
			result.addItem(catalog);
		}
		
		return result;
	}
}
}