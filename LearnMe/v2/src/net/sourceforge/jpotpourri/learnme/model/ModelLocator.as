package net.sourceforge.jpotpourri.learnme.model {

import net.sourceforge.jpotpourri.learnme.vo.ICheckedQuestion;
import net.sourceforge.jpotpourri.learnme.vo.IQuestionCatalog;
import net.sourceforge.jpotpourri.learnme.vo.IQuestionary;
	

[Bindable]
public class ModelLocator {
	
	private static const INSTANCE:ModelLocator = new ModelLocator();
	
	
	public var questionary: IQuestionary;
	
	public var currentQuestion: ICheckedQuestion;
	
	public var currentCatalog: IQuestionCatalog;
	
	
	public function ModelLocator() {
	}

	public static function get instance(): ModelLocator {
		return INSTANCE;
	}

}
}

