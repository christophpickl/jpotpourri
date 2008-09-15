package net.sourceforge.jpotpourri.learnme.dao {

internal class AbstractDao {
	
	private static const DB: Database = Database.instance;
	
	public function AbstractDao() {
	}

	public function execSql(sql: String, fnResult: Function = null, params: Array = null): void {
		DB.execSql(sql, fnResult, params);
	}
	
	protected function get lastInsertId(): int {
		return DB.lastInsertId;
	}
	
}
}