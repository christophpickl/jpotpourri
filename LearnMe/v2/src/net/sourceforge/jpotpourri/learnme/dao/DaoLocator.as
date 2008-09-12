package net.sourceforge.jpotpourri.learnme.dao {

import logging.Logger;
	

public class DaoLocator {
	
	private static const LOG:Logger = Logger.getLogger("net.sourceforge.jpotpourri.learnme.dao.DaoLocator");
	private static const INSTANCE: DaoLocator = new DaoLocator();
	
	public function DaoLocator() {
	}

	public static function get instance(): DaoLocator {
		return INSTANCE;
	}
	
	public function get catalogDao(): ICatalogDao {
		return CatalogDao.instance;
	}
	
	public function connect(fnConnected: Function): void {
		LOG.fine("connect() invoked");
		Database.instance.connect(fnConnected);
	}
}
}