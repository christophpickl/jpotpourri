package code.common {

import code.vo.Library;

import com.adobe.cairngorm.model.IModelLocator;

[Bindable]
public class Model implements IModelLocator {
	
	private static var INSTANCE: Model;
	
	
	public var library: Library = Library.newDefault();
	// public var library: Library = LibraryDao.instance.load();
	
	
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
