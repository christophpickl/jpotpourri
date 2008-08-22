package net.sourceforge.teabee.business {

import mx.collections.ArrayCollection;

import net.sourceforge.teabee.valueobject.IPlayable;


public class PlayDelegate {
	
	private static var _instance:PlayDelegate;
	
	private var _listener:ArrayCollection = new ArrayCollection();
	
	
	public function PlayDelegate(singletonEnforcer:SingletonEnforcer) {
		
	}

	public static function get instance():PlayDelegate {
		if(_instance == null) {
			_instance = new PlayDelegate(new SingletonEnforcer());
		}
		return _instance;
	}

	public function doPlay(playable:IPlayable):void {
		this.broadcastDoPlay(playable);
	}

	public function addListener(doPlayFunction:Function):void {
		this._listener.addItem(doPlayFunction);
	}
	
	private function broadcastDoPlay(playable:IPlayable):void {
		for each (var doPlayFunction:Function in this._listener) {
			doPlayFunction(playable);
		}
	}
}
}

class SingletonEnforcer {}
