package net.sourceforge.jpotpourri.learnme.dao {

import flash.data.SQLConnection;
import flash.data.SQLResult;
import flash.data.SQLStatement;
import flash.events.SQLErrorEvent;
import flash.events.SQLEvent;
import flash.filesystem.File;

import logging.Logger;

import mx.collections.ArrayCollection;
	

internal class Database {
	
	private static const LOG:Logger = Logger.getLogger("net.sourceforge.jpotpourri.learnme.dao.Database");
	private static const INSTANCE: Database = new Database();
	
	private var connected:Boolean = false;
	private var sqlConnection:SQLConnection;
	private var stmt:SQLStatement;
	private var _fnResult: Function;
	private var fnConnected: Function;
	
	public function Database() {
		this.sqlConnection = new SQLConnection();
		this.sqlConnection.addEventListener(SQLEvent.OPEN, onSqlConnectionOpen);
		this.sqlConnection.addEventListener(SQLErrorEvent.ERROR, onSqlConnectionError);
	}
	
	public static function get instance(): Database {
		return INSTANCE;
	}
	
	public function connect(fnConnected: Function = null): void {
		if(this.connected == true) {
			return;
		}
		var dbFile:File = File.applicationStorageDirectory.resolvePath("database.db");
		LOG.fine("opening database file [" + dbFile.nativePath + "] ...");
		this.fnConnected = fnConnected;
		this.sqlConnection.openAsync(dbFile);
	}
	
	private function onSqlConnectionOpen(event:SQLEvent):void {
		LOG.info("connection to sql database established.");
		this.connected = true;
		if(this.fnConnected != null) {
			this.fnConnected();
		}
	}
	
	private function onSqlConnectionError(event:SQLErrorEvent):void {
		LOG.severe("onSqlError(event=" + event + ")");
	}
	
	/**
	 * @param fnResult Function with signature: (result: ArrayCollection): void
	 **/
	public function execSql(sql: String, fnResult: Function = null): void {
		this.connect();
		_fnResult = fnResult;
		this.stmt = new SQLStatement();
		this.stmt.sqlConnection = this.sqlConnection;
		this.stmt.text = sql;
		this.stmt.addEventListener(SQLEvent.RESULT, onSqlStmtResult);
		this.stmt.addEventListener(SQLErrorEvent.ERROR, onSqlStmtError);
		LOG.fine("executing sql ["+sql+"]");
		this.stmt.execute();
	}
	
	private function onSqlStmtResult(event:SQLEvent):void {
		LOG.info("onSqlStmtResult(event=" + event + ")");
		var rs:SQLResult = this.stmt.getResult();
		
		if(rs != null && rs.data != null) {
			var result:ArrayCollection = new ArrayCollection(rs.data as Array);
			LOG.finer("got back database result.");
			if(_fnResult != null) {
				_fnResult(result);
			}
		}
	}
	private function onSqlStmtError(event:SQLErrorEvent):void {
		LOG.severe("onSqlStmtError(event=" + event + ")");
	}

}
}
