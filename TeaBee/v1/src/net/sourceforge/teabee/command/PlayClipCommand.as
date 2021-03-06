package net.sourceforge.teabee.command {

import com.adobe.cairngorm.commands.ICommand;
import com.adobe.cairngorm.control.CairngormEvent;

import logging.Logger;

import mx.collections.ArrayCollection;

import net.sourceforge.teabee.business.PlayDelegate;
import net.sourceforge.teabee.event.PlayClipEvent;
import net.sourceforge.teabee.valueobject.IPlayable;
import net.sourceforge.teabee.valueobject.JukeboxList;

public class PlayClipCommand implements ICommand {
	
	private static const LOG:Logger = Logger.getLogger("net.sourceforge.teabee.command.PlayClipCommand");
	
	public function PlayClipCommand() {
		
	}

	public function execute(_event:CairngormEvent):void {
		const event:PlayClipEvent = _event as PlayClipEvent;
		LOG.fine("execute(event=" + event + ")");
		
		const arr:ArrayCollection = new ArrayCollection();
		arr.addItem(event.clip as IPlayable);
		const jukeboxList:JukeboxList = new JukeboxList(arr);
		
		PlayDelegate.instance.doPlay(jukeboxList);
	}
	
}
}