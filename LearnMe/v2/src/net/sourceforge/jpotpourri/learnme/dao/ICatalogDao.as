package net.sourceforge.jpotpourri.learnme.dao {

import net.sourceforge.jpotpourri.learnme.vo.IQuestionCatalog;
	

public interface ICatalogDao {
	
	function insertCatalog(catalog: IQuestionCatalog): void;
	
	/** fnResult: onCatalogsFetched(catalogs: ArrayCollection<IQuestionCatalog>): void */
	function selectCatalogs(fnResult: Function): void;
	
	/** does not give back concrete data about datalog, but only amount with same title */
	function selectCatalogsByTitle(title: String, fnResult: Function): void;
	
}
}