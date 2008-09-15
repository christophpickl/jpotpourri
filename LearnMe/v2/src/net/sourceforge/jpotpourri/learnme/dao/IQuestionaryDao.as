package net.sourceforge.jpotpourri.learnme.dao {

import net.sourceforge.jpotpourri.learnme.vo.IQuestionCatalog;
import net.sourceforge.jpotpourri.learnme.vo.IQuestionary;
	


public interface IQuestionaryDao {
	
	function insertQuestionary(questionary: IQuestionary): void;
	
	function selectQuestionariesForCatalog(catalog: IQuestionCatalog, fnResult: Function): void;
	
}
}