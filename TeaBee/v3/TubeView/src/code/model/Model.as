package code.model {

import code.model.vo.Clip;
import code.model.vo.Library;
import code.model.vo.Playlist;

import com.adobe.cairngorm.model.IModelLocator;

import mx.collections.ArrayCollection;

[Bindable]
public class Model implements IModelLocator {
	
	private static var INSTANCE: Model;
	
	
	public var library: Library = Library.newDefault();
	// public var library: Library = LibraryDao.instance.load();
	
	/** currently selected */
	public var playlist: Playlist;
	
	/** currently selected */
	public var clip: Clip;
	
	/** ArrayCollection<SearchResult>*/
	public var searchResults: ArrayCollection = new ArrayCollection();
	
	
	public function Model(singletonEnforcer:SingletonEnforcer) {
		
	}


	public static function get instance():Model {
		if(INSTANCE == null) {
			INSTANCE = new Model(new SingletonEnforcer());
		}
		return INSTANCE;
	}

}
}

class SingletonEnforcer { }
