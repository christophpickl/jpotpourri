package code.model.dao {

public class DaoLocator {
	
	private static const INSTANCE: DaoLocator = new DaoLocator();
	
	private var _libraryDao: ILibraryDao;
	
	public function DaoLocator() {
		this._libraryDao = new LibraryDao();
	}


	public static function get instance(): DaoLocator {
		return INSTANCE;
	}
	
	public function get libraryDao(): ILibraryDao {
		return this._libraryDao;
	}

}
}