package net.sourceforge.jpotpourri.learnme.dao {

import logging.Logger;

import mx.collections.ArrayCollection;

import net.sourceforge.jpotpourri.learnme.vo.IQuestionCatalog;
import net.sourceforge.jpotpourri.learnme.vo.QuestionCatalog;
	

internal class CatalogDao implements ICatalogDao {
	
	private static const LOG:Logger = Logger.getLogger("net.sourceforge.jpotpourri.learnme.dao.CatalogDao");
	private static const DB: Database = Database.instance;
	private static const SQL_CREATE:String = 
		"CREATE TABLE IF NOT EXISTS catalog (" +
		  "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
		  "title CHAR(255)" +
		")";
	
	// must be last static stuff in here	
	private static const INSTANCE: CatalogDao = new CatalogDao();
	
	private var _fnResult: Function;
	
	
	public function CatalogDao() {
		DB.execSql(SQL_CREATE);
	}

	public static function get instance(): CatalogDao {
		return INSTANCE;
	}
	
	private var currentInsertedCatalog: IQuestionCatalog;
	public function insertCatalog(catalog: IQuestionCatalog): void {
		LOG.info("inserting question catalog: " + catalog);
		
		DB.execSql("INSERT INTO catalog (title) VALUES ('"+catalog.title+"')");
		DB.execSql("SELECT last_insert_rowid() AS last_id", updateLastInsertId);
	}
	
	private function updateLastInsertId(result: ArrayCollection): void {
		var lastId: int = result.getItemAt(0).last_id;
		LOG.finer("Setting id for inserted catalog to ["+lastId+"].");
		this.currentInsertedCatalog.id = lastId;
	}
	
	
	
	public function selectCatalogs(fnResult: Function): void {
		DB.execSql("SELECT * FROM catalog", this.onSelectResult);
		_fnResult = fnResult;
	}
	
	public function selectCatalogsByTitle(title: String, fnResult: Function): void {
		_fnResult = fnResult;
		DB.execSql("SELECT * FROM catalog WHERE title='"+title+"'", onSelectResult);
		
	}
	
	private function onSelectResult(result: ArrayCollection): void {
		LOG.finer("onSelectResult for "+result.length+" items.");
		_fnResult(convertObjectsToCatalogs(result));
	}
	
	private static function convertObjectsToCatalogs(data: ArrayCollection): ArrayCollection {
		const result: ArrayCollection = new ArrayCollection();
		for each(var obj: Object in data) {
			var sourceQuestions: ArrayCollection = new ArrayCollection();
			result.addItem(new QuestionCatalog(obj.id, obj.title, sourceQuestions));
		}
		return result;
	}
}
}