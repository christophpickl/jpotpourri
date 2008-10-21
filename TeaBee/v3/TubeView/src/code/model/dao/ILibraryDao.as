package code.model.dao {

import code.model.vo.Library;
	

public interface ILibraryDao {
	
	function insertLibrary(library: Library): void;
	
	/**
	 * @param fnFetchedlibrary(library:Library): void
	 **/
	function fetchLibrary(fnFetchedLibrary: Function): void;
	
	function clearLibrary(): void;
	
}
}