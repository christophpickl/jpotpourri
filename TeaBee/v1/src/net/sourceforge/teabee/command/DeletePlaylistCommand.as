package net.sourceforge.teabee.command {

import com.adobe.cairngorm.commands.ICommand;
import com.adobe.cairngorm.control.CairngormEvent;

import logging.Logger;

import net.sourceforge.teabee.business.DeleteDelegate;
import net.sourceforge.teabee.event.DeletePlaylistEvent;

public class DeletePlaylistCommand implements ICommand {
	
	private static const LOG:Logger = Logger.getLogger("net.sourceforge.teabee.command.DeletePlaylistCommand");
	
	public function DeletePlaylistCommand() {
	}

	public function execute(_event:CairngormEvent):void {
		const event:DeletePlaylistEvent = _event as DeletePlaylistEvent;
		LOG.fine("execute(event=" + event + ")");
		
		DeleteDelegate.instance.doDeletePlaylist(event.playlist);		
	}
	
}
}