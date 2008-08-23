package net.sourceforge.teabee.business {

import mx.collections.ArrayCollection;

import net.sourceforge.teabee.view.libtree.INode;
	

public class TreeChangeProvider {
	
	private static var _instance:TreeChangeProvider;
	
	// FIXME use TreeDataDescriptor instead ... would also solve parentNode problem
	private var _addListeners:ArrayCollection = new ArrayCollection();
	private var _resetListeners:ArrayCollection = new ArrayCollection();
	
	
	public function TreeChangeProvider(singletonEnforcer:SingletonEnforcer) {
		
	}
	
	public static function get instance():TreeChangeProvider {
		if(_instance == null) {
			_instance = new TreeChangeProvider(new SingletonEnforcer());
		}
		return _instance;
	}
	

	public function addAddListener(didAddFunction:Function):void {
		this._addListeners.addItem(didAddFunction);
	}
	public function addResetListener(didResetFunction:Function):void {
		this._resetListeners.addItem(didResetFunction);
	}
	
	internal function broadcastDidAdd(node:INode):void {
		for each (var didAddFunction:Function in this._addListeners) {
			didAddFunction(node);
		}
	}
	
	internal function broadcastDidReset():void {
		for each (var didResetFunction:Function in this._resetListeners) {
			didResetFunction();
		}
	}

}
}

class SingletonEnforcer {}
