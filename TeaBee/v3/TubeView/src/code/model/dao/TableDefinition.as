package code.model.dao {

internal class TableDefinition {
	
	public var name: String;
	
	public var sqlCreate: String;
	
	public var sqlInsert: String;
	
	public function TableDefinition(name: String, sqlCreate: String, sqlInsert: String) {
		this.name = name;
		this.sqlCreate = "CREATE TABLE IF NOT EXISTS " + name + " (" + sqlCreate + ")";
		this.sqlInsert = sqlInsert;
	}

}
}