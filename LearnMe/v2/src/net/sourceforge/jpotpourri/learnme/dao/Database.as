package net.sourceforge.jpotpourri.learnme.dao {

import flash.data.SQLConnection;
import flash.data.SQLStatement;
import flash.events.SQLErrorEvent;
import flash.events.SQLEvent;
import flash.filesystem.File;

import logging.Logger;

import mx.collections.ArrayCollection;
	

public class Database {
	
	private static const LOG:Logger = Logger.getLogger("net.sourceforge.jpotpourri.learnme.dao.Database");
	private static const INSTANCE: Database = new Database();
	
	private var dbConnect:Boolean = false;
	private var sqlConnection:SQLConnection;
	private var stmt:SQLStatement;
	
	public function Database() {
		this.sqlConnection = new SQLConnection();
		this.sqlConnection.addEventListener(SQLEvent.OPEN, onSqlConnectionOpen);
		this.sqlConnection.addEventListener(SQLErrorEvent.ERROR, onSqlConnectionError);
	}
	
	public static function get instance(): Database {
		return INSTANCE;
	}
	
	private function connect(): void {
		if(this.dbConnect == true) {
			return;
		}
		var dbFile:File = File.applicationStorageDirectory.resolvePath("database.db");
		LOG.fine("opening database file [" + dbFile.nativePath + "] ...");
		this.sqlConnection.openAsync(dbFile);
	}
	
	private function onSqlConnectionOpen(event:SQLEvent):void {
		this.dbConnect = true;
	}
	
	private function onSqlConnectionError(event:SQLErrorEvent):void {
		LOG.severe("onSqlError(event=" + event + ")");
	}
	
	public function execSql(sql: String): void {
		this.connect();
		
		this.stmt = new SQLStatement();
		this.stmt.sqlConnection = this.sqlConnection;
		// var sql:String = "CREATE TABLE IF NOT EXISTS user (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT)";
		this.stmt.text = sql;
		this.stmt.addEventListener(SQLEvent.RESULT, onSqlStmtResult);
		this.stmt.addEventListener(SQLErrorEvent.ERROR, onSqlStmtError);
		LOG.fine("executing sql ["+sql+"]");
		this.stmt.execute();
	}
	
	private function onSqlStmtResult(event:SQLEvent):void {
		LOG.info("onSqlStmtResult(event=" + event + ")");
		var rs:SQLResult = this.stmt.getResult();
		
		if(rs.data != null) {
			var result:ArrayCollection = new ArrayCollection(rs.data as Array);
			trace("got result: " + result);
		}
	}
	private function onSqlStmtError(event:SQLErrorEvent):void {
		LOG.severe("onSqlStmtError(event=" + event + ")");
	}

}
}
