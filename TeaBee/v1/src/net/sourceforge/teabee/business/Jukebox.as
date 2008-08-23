package net.sourceforge.teabee.business {

import logging.Logger;

import net.sourceforge.teabee.event.PlayerEvent;
import net.sourceforge.teabee.valueobject.IPlayable;
import net.sourceforge.teabee.valueobject.JukeboxList;
import net.sourceforge.teabee.view.Player;
	

[Bindable]
public class Jukebox {
	
	private static const LOG:Logger = Logger.getLogger("net.sourceforge.teabee.business.Jukebox");
	
	public var list:JukeboxList;
	
	private var _player:Player;
	
	public function Jukebox(player:Player) {
		this._player = player;
		
		this._player.addEventListener(PlayerEvent.PLAYER_FINISHED, onPlayerFinished);
		
		PlayDelegate.instance.addListener(doPlay);
	}
	
	private function doPlay(list:JukeboxList):void {
		this.list = list;
		
		if(list.currentPlayable == null) {
			list.currentPlayable = list.playables.getItemAt(0) as IPlayable;
		}
		
		list.currentPlayable = getNextPlayablePlayble(this.list.playables.getItemIndex(list.currentPlayable));
		
		
		this._player.doPlayPlayable(list.currentPlayable);
	}

	private function onPlayerFinished(event:PlayerEvent):void {
		LOG.fine("onPlayerFinished(event="+event+")");
		var curIndex:uint = this.list.playables.getItemIndex(this.list.currentPlayable);
		LOG.finest("index of current playable: " + curIndex + " (next will be: "+(curIndex+1)+")");
		curIndex++;
		
		list.currentPlayable = getNextPlayablePlayble(curIndex);
		
		this._player.doPlayPlayable(this.list.currentPlayable);
	}
	
	/** depending on url != null */
	private function getNextPlayablePlayble(index:uint):IPlayable {
		if(index  >= this.list.playables.length) {
			LOG.finest("Rewinding jukebox list from beginning because curIndex > list.playables.length ["+list.playables.length+"].");
			index = 0; // rewind
		}
		var playable:IPlayable = this.list.playables.getItemAt(index) as IPlayable;
		
		while(playable.url == null) {
			LOG.finest("getNextPlayablePlayble looking for next playable because current.url is null ["+playable+"]");
			index++;
			if(index  >= this.list.playables.length) {
				LOG.finest("Rewinding jukebox list from beginning because curIndex > list.playables.length ["+list.playables.length+"].");
				index = 0; // rewind
			}
			playable = this.list.playables.getItemAt(index) as IPlayable;
		}
		return playable;
	}
}
}