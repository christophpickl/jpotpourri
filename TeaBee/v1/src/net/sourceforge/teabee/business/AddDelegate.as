package net.sourceforge.teabee.business {

import net.sourceforge.teabee.model.Model;
import net.sourceforge.teabee.valueobject.Clip;
import net.sourceforge.teabee.valueobject.Playlist;
import net.sourceforge.teabee.valueobject.SearchResult;
import net.sourceforge.teabee.view.libtree.INode;
import net.sourceforge.teabee.view.libtree.INodeContainer;

public class AddDelegate {
	
	private static var _instance:AddDelegate;
	
	public function AddDelegate(singletonEnforcer:SingletonEnforcer) {
		
	}

	public static function get instance():AddDelegate {
		if(_instance == null) {
			_instance = new AddDelegate(new SingletonEnforcer());
		}
		return _instance;
	}

	public function doAddClip(playlist:Playlist, searchResult:SearchResult):void {
		const clip:Clip = new Clip(searchResult.title, searchResult.url, searchResult.duration, searchResult.thumbnail);
		playlist.clips.addItem(clip);
	}

	public function doAdd(newNode:INode, target:INodeContainer):void {
		if(target == null) {
			target = Model.instance.library;
		}
		
		newNode.parentNode = target;
		newNode.parentNode.children.addItem(newNode);
		
		TreeChangeProvider.instance.broadcastDidAdd(newNode);
	}
	
}
}

class SingletonEnforcer {}
