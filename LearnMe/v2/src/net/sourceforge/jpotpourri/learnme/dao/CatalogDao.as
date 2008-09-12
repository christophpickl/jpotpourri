package net.sourceforge.jpotpourri.learnme.dao {

import logging.Logger;

import mx.collections.ArrayCollection;

import net.sourceforge.jpotpourri.learnme.vo.IQuestionCatalog;
import net.sourceforge.jpotpourri.learnme.vo.ISourceAnswer;
import net.sourceforge.jpotpourri.learnme.vo.ISourceQuestion;
import net.sourceforge.jpotpourri.learnme.vo.MultipleChoiceSourceAnswer;
import net.sourceforge.jpotpourri.learnme.vo.MultipleChoiceSourceQuestion;
import net.sourceforge.jpotpourri.learnme.vo.QuestionCatalog;
	

internal class CatalogDao implements ICatalogDao {
	
	private static const LOG:Logger = Logger.getLogger("net.sourceforge.jpotpourri.learnme.dao.CatalogDao");
	private static const DB: Database = Database.instance;
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
		  "text TEXT" +
		  "feedback TEXT, " +
		  "correct BOOLEAN " +
		")";
		
	private static const SQL_SELECT_ALL: String = 
		"SELECT " +
		  "c.id AS c_id, c.title AS c_title, " +
		  "q.id AS q_id, q.title AS q_title, q.text AS q_text, " +
		  "a.id AS a_id, a.text AS a_text, a.correct AS a_correct " +
		"FROM catalog AS c " +
		  "JOIN s_question AS q ON c.id = q.id_catalog " +
		  "JOIN s_answer AS a   ON q.id = a.id_s_question ";
	
	// must be last static stuff in here	
	private static const INSTANCE: CatalogDao = new CatalogDao();
	
	private var _fnResult: Function;
	
	private var currentInsertedCatalog: IQuestionCatalog;
	
	
	public function CatalogDao() {
		DB.execSql(SQL_CREATE_CATALOG);
		DB.execSql(SQL_CREATE_S_QUESTION);
		DB.execSql(SQL_CREATE_S_ANSWER);
	}

	public static function get instance(): CatalogDao {
		return INSTANCE;
	}
	
	public function insertCatalog(catalog: IQuestionCatalog): void {
		LOG.info("inserting question catalog: " + catalog);
		
		DB.execSql("INSERT INTO catalog (title) VALUES ('"+catalog.title+"')");
		this.currentInsertedCatalog = catalog;
		
		var lastCatalogId: int = DB.lastInsertId;
		LOG.finer("Setting id for inserted catalog to ["+lastCatalogId+"].");
		this.currentInsertedCatalog.id = lastCatalogId;
		
		for each(var question: ISourceQuestion in currentInsertedCatalog.sourceQuestions) {
			LOG.finer("inserting question: " + question);
			DB.execSql("INSERT INTO s_question (id_catalog, title, text) VALUES " +
					"("+lastCatalogId+", '"+question.title+"', '"+question.text+"')");
			var lastQuestionId: int = DB.lastInsertId;
			
			for each(var answer: ISourceAnswer in question.sourceAnswers) {
				DB.execSql("INSERT INTO s_answer (id_s_question, text, correct) VALUES " +
					"("+lastQuestionId+", '"+answer.text+"', "+answer.correct+")");
			}
		}
		
		this.currentInsertedCatalog = null;
	}
	
	
	
	
	public function selectCatalogs(fnResult: Function): void {
		_fnResult = fnResult;
		DB.execSql(SQL_SELECT_ALL, this.onSelectCatalogsResult);
		
	}
	
	public function selectCatalogsByTitle(title: String, fnResult: Function): void {
		_fnResult = fnResult;
		DB.execSql("SELECT id FROM catalog WHERE title='"+title+"'", onSelectCatalogsResult);
		
	}
	
	private function onSelectCatalogsResult(result: ArrayCollection): void {
		LOG.finer("onSelectCatalogsResult for "+result.length+" items.");
		
		_fnResult(convertObjectsToCatalogs(result));
	}
	/*
	"SELECT " +
	  "c.id AS c_id, c.title AS c_title," +
	  "q.id AS q_id, q.title AS q_title, q.text AS q_text," +
	  "a.id AS a_id, a.text AS a_text, a.correct AS a_correct" +
	"FROM catalog AS c" +
	  "JOIN s_question AS q ON c.id = q.id_catalog" +
	  "JOIN s_answer AS a   ON q.id = a.id_s_question";
	*/
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
			if(lastCatalogId != obj.c_id) {
				// new catalog
				if(catalog != null) {
					// store back recent finished catalog
					result.addItem(catalog);
				}
				questions = new ArrayCollection();
				catalog = new QuestionCatalog(obj.c_id, obj.c_title, questions);
				lastCatalogId = obj.c_id;
			}
			
			if(lastQuestionId != obj.q_id) {
				// new question
				if(question != null) {
					// store back recent finished question
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