package net.sourceforge.jpotpourri.learnme.dao {


import mx.collections.ArrayCollection;

import net.sourceforge.jpotpourri.learnme.vo.IQuestionCatalog;


public interface IReportDao {
	
	/** ArrayCollection<Report> */
	function selectReport(catalog: IQuestionCatalog): ArrayCollection;
	
}
}