package net.sourceforge.jpotpourri.learnme.dao {


import mx.collections.ArrayCollection;

import net.sourceforge.jpotpourri.learnme.vo.IQuestionCatalog;


public interface IReportDao {
	
	/** fnResult: ArrayCollection<Report> */
	function selectReports(catalog: IQuestionCatalog, fnResult: Function): void;
	
}
}