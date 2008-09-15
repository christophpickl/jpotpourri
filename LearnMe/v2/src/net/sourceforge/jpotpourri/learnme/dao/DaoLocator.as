package net.sourceforge.jpotpourri.learnme.dao {

import logging.Logger;
	

public class DaoLocator {
	
	private static const LOG:Logger = Logger.getLogger("net.sourceforge.jpotpourri.learnme.dao.DaoLocator");
	private static const INSTANCE: DaoLocator = new DaoLocator();
	
	private var _catalogDao: ICatalogDao;
	private var _questionaryDao: IQuestionaryDao;
	private var _reportDao: IReportDao;
	
	public function DaoLocator() {
		this._catalogDao = new CatalogDao();
		this._questionaryDao = new QuestionaryDao();
		this._reportDao = new ReportDao();
	}

	public static function get instance(): DaoLocator {
		return INSTANCE;
	}
	
	public function get catalogDao(): ICatalogDao {
		return this._catalogDao;
	}
	
	public function get questionaryDao(): IQuestionaryDao {
		return this._questionaryDao;
	}
	
	public function get reportDao(): IReportDao {
		return this._reportDao;
	}
	
	public function connect(fnConnected: Function): void {
		Database.instance.connect(fnConnected);
	}
	
	public function get connected(): Boolean {
		return Database.instance.connected;
	}
}
}