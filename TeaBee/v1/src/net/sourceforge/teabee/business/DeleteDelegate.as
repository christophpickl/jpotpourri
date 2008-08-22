package net.sourceforge.teabee.business {

import net.sourceforge.teabee.model.Model;
import net.sourceforge.teabee.valueobject.Folder;
import net.sourceforge.teabee.valueobject.Playlist;
import net.sourceforge.teabee.view.libtree.INode;
import net.sourceforge.teabee.view.libtree.INodeContainer;


public class DeleteDelegate {
	
	private static var _instance:DeleteDelegate;
	
	// private var _listener:ArrayCollection = new ArrayCollection();
	
	
	public function DeleteDelegate(singletonEnforcer:SingletonEnforcer) {
		
	}

	public static function get instance():DeleteDelegate {
		if(_instance == null) {
			_instance = new DeleteDelegate(new SingletonEnforcer());
		}
		return _instance;
	}

	public function doDeletePlaylist(playlist:Playlist):void {
		this.doDelete(playlist);
	}
	
	public function doDeleteFolder(folder:Folder):void {
		this.doDelete(folder);
	}
	
	private function doDelete(node:INode):void {
		const parentNode:INodeContainer = node.parentNode;
		
		const childIndex:int = parentNode.children.getItemIndex(node);
		if(childIndex < 0) {
			throw new Error("Could not get index ["+childIndex+"] for node ["+node+"] by parent ["+parentNode+"]!");
		}
		
		parentNode.children.removeItemAt(childIndex);
		
		Model.instance.selectedPlaylist = null;
		Model.instance.selectedClip = null;
	}

	/*
	public function addListener(didDeleteFunction:Function):void {
		this._listener.addItem(didDeleteFunction);
	}
	
	private function broadcastDidDelete(node:INode):void {
		for each (var didDeleteFunction:Function in this._listener) {
			didDeleteFunction(node);
		}
	}
	*/
}
}

class SingletonEnforcer {}
