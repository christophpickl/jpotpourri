package net.sourceforge.teabee.command {

import com.adobe.cairngorm.commands.ICommand;
import com.adobe.cairngorm.control.CairngormEvent;

import logging.Logger;

import mx.collections.ArrayCollection;

import net.sourceforge.teabee.business.PlayDelegate;
import net.sourceforge.teabee.event.PlayPlaylistEvent;
import net.sourceforge.teabee.valueobject.Clip;
import net.sourceforge.teabee.valueobject.IPlayable;
import net.sourceforge.teabee.valueobject.JukeboxList;

public class PlayPlaylistCommand implements ICommand {
	
	private static const LOG:Logger = Logger.getLogger("net.sourceforge.teabee.command.PlayPlaylistCommand");
	
	public function PlayPlaylistCommand():void {
		
	}

	public function execute(_event:CairngormEvent):void {
		const event:PlayPlaylistEvent = _event as PlayPlaylistEvent;
		LOG.fine("execute(event=" + event + ")");
		
		const arr:ArrayCollection = new ArrayCollection();
		for each(var clip:Clip in event.playlist.clips) {
			arr.addItem(clip as IPlayable);
		}
		
		const jukeboxList:JukeboxList = new JukeboxList(arr);
		jukeboxList.currentPlayable = event.clip;
		
		PlayDelegate.instance.doPlay(jukeboxList);
		
	}
	
}
}