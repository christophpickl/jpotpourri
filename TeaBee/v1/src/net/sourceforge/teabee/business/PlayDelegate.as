package net.sourceforge.teabee.business {

import logging.Logger;

import mx.collections.ArrayCollection;

import net.sourceforge.teabee.valueobject.IPlayable;
import net.sourceforge.teabee.valueobject.JukeboxList;


public class PlayDelegate {
	
	private static const LOG:Logger = Logger.getLogger("net.sourceforge.teabee.business.PlayDelegate");
	
	
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

	public function doPlay(jukeboxList:JukeboxList):void {
		LOG.info("doPlay(jukeboxList="+jukeboxList+")");
		this.broadcastDoPlay(jukeboxList);
	}

	public function addListener(doPlayFunction:Function):void {
		this._listener.addItem(doPlayFunction);
	}
	
	private function broadcastDoPlay(jukeboxList:JukeboxList):void {
		LOG.finest("broadcastDoPlay(jukeboxList="+jukeboxList+") listener.length: " + this._listener.length);
		for each (var doPlayFunction:Function in this._listener) {
			doPlayFunction(jukeboxList);
		}
	}
}
}

class SingletonEnforcer {}
