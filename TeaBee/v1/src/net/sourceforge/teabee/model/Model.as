package net.sourceforge.teabee.model {

import com.adobe.cairngorm.model.IModelLocator;

import mx.collections.ArrayCollection;

import net.sourceforge.teabee.valueobject.Clip;
import net.sourceforge.teabee.valueobject.Library;
import net.sourceforge.teabee.valueobject.Playlist;

[Bindable]
public class Model implements IModelLocator {
	
	private static var INSTANCE:Model; 
	
	
	public var library:Library = Library.newDefault;
	
	public var searchResult:ArrayCollection = new ArrayCollection();
	
	public var isSearching:Boolean = false;
	
	public var selectedPlaylist:Playlist = null;
	
	public var selectedClip:Clip = null;
	
	
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

class SingletonEnforcer {}
