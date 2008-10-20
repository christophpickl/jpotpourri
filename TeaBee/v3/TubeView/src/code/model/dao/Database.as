package code.model.dao {

import flash.data.SQLConnection;
import flash.data.SQLResult;
import flash.data.SQLStatement;
import flash.events.SQLErrorEvent;
import flash.events.SQLEvent;
import flash.filesystem.File;

import logging.Logger;

import mx.collections.ArrayCollection;
	

internal class Database {
	
	private static const LOG:Logger = Logger.getLogger("code.model.dao.Database");
	
	private static const INSTANCE: Database = new Database();
	
	private var _connected:Boolean = false;
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
	
	public function get connected(): Boolean {
		return this._connected;
	}
	
	public function connect(fnConnected: Function = null): void {
		if(this._connected == true) {
			return;
		}
		var dbFile:File = File.applicationStorageDirectory.resolvePath("database.db");
		LOG.fine("opening database file [" + dbFile.nativePath + "]");
		this.fnConnected = fnConnected;
		// this.sqlConnection.openAsync(dbFile);
		this.sqlConnection.open(dbFile); // use synchronized model
	}
	
	private function onSqlConnectionOpen(event:SQLEvent):void {
		LOG.info("connection to sql database established.");
		this._connected = true;
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
	public function execSql(sql: String, fnResult: Function = null, params: Array = null): void {
		this.connect();
		_fnResult = fnResult;
		this.stmt = new SQLStatement();
		this.stmt.sqlConnection = this.sqlConnection;
		this.stmt.text = sql;
		
		if(params != null) {
			for(var key: String in params) {
				LOG.finest("replacing statment parameter ["+key+"] with value ["+params[key]+"].");
				this.stmt.parameters[key] = params[key];
			}
		}
		
		this.stmt.addEventListener(SQLEvent.RESULT, onSqlStmtResult);
		this.stmt.addEventListener(SQLErrorEvent.ERROR, onSqlStmtError);
		LOG.fine("executing sql ["+sql+"]");
		this.stmt.execute();
	}
	
	public function get lastInsertId(): int {
		return this.sqlConnection.lastInsertRowID;
	}
	
	private function onSqlStmtResult(event:SQLEvent):void {
		LOG.info("onSqlStmtResult(event=" + event + ")");
		var rs:SQLResult = this.stmt.getResult();
		
		var result:ArrayCollection;
		if(rs == null) {
			result = null;
		} else {
			LOG.finer("got back database result.");
			if(rs.data == null) {
				result = new ArrayCollection();
			} else {
				result = new ArrayCollection(rs.data as Array);
			}
		}

		if(_fnResult != null) {
			_fnResult(result);
		} else if(rs != null) {
			LOG.warning("No fnResult set although we got back a result set!");
		}
	}
	
	private function onSqlStmtError(event:SQLErrorEvent):void {
		LOG.severe("onSqlStmtError(event=" + event + ")");
	}

}
}
