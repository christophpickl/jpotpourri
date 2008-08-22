package net.sourceforge.teabee.dao {

public class LibraryDao {
	
	private static var _instance:ILibraryDao;
	
	public function LibraryDao() {
		throw new Error("LibraryDao must not be instantiated!");
	}

	public static function get instance():ILibraryDao {
		if(_instance == null) {
			_instance = new SharedObjectLibraryDao();
		}
		return _instance;
	}

}
}