package code.model.dao {

internal class TableDefinition {
	
	private var _name: String;
	
	private var _sqlCreate: String;
	
	private var _sqlInsert: String;
	
	private var _sqlSelect: String;
	
	public function TableDefinition(name: String, sqlCreate: String, sqlInsert: String, sqlSelect: String) {
		this._name = name;
		this._sqlCreate = "CREATE TABLE IF NOT EXISTS " + name + " (" + sqlCreate + ")";
		this._sqlInsert = sqlInsert;
		this._sqlSelect = sqlSelect;
	}

	public function get name(): String {
		return this._name;
	}
	public function get sqlCreate(): String {
		return this._sqlCreate;
	}
	public function get sqlInsert(): String {
		return this._sqlInsert;
	}
	public function get sqlSelect(): String {
		return this._sqlSelect;
	}
	public function get sqlDelete(): String {
		return "DELETE FROM " + this._name;
	}
	
}
}
