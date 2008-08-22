package net.sourceforge.teabee.business {

import mx.collections.ArrayCollection;

import net.sourceforge.teabee.model.Model;
import net.sourceforge.teabee.view.libtree.INode;
import net.sourceforge.teabee.view.libtree.INodeContainer;

public class AddDelegate {
	
	private static var _instance:AddDelegate;
	
	private var _listener:ArrayCollection = new ArrayCollection();
	
	
	public function AddDelegate(singletonEnforcer:SingletonEnforcer) {
		
	}

	public static function get instance():AddDelegate {
		if(_instance == null) {
			_instance = new AddDelegate(new SingletonEnforcer());
		}
		return _instance;
	}

	public function doAdd(newNode:INode, target:INodeContainer):void {
		if(target == null) {
			target = Model.instance.library;
		}
		
		newNode.parentNode = target;
		newNode.parentNode.children.addItem(newNode);
		this.broadcastDidAdd(newNode);
	}

	public function addListener(didAddFunction:Function):void {
		this._listener.addItem(didAddFunction);
	}
	
	private function broadcastDidAdd(node:INode):void {
		for each (var didAddFunction:Function in this._listener) {
			didAddFunction(node);
		}
	}
}
}

class SingletonEnforcer {}
