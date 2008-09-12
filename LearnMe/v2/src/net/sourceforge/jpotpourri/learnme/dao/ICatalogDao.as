package net.sourceforge.jpotpourri.learnme.dao {

import net.sourceforge.jpotpourri.learnme.vo.IQuestionCatalog;
	

public interface ICatalogDao {
	
	function insertCatalog(catalog: IQuestionCatalog): void;
	
	function selectCatalogs(fnResult: Function): void;
	
	function selectCatalogsByTitle(title: String, fnResult: Function): void;
	
}
}