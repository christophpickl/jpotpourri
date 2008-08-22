package net.sourceforge.teabee.command {

import com.adobe.cairngorm.commands.ICommand;
import com.adobe.cairngorm.control.CairngormEvent;

import logging.Logger;

import net.sourceforge.teabee.business.AddDelegate;
import net.sourceforge.teabee.event.AddPlaylistEvent;
import net.sourceforge.teabee.valueobject.Playlist;
	
public class AddPlaylistCommand implements ICommand {
	
	private static const LOG:Logger = Logger.getLogger("net.sourceforge.teabee.command.AddPlaylistCommand");
	
	public function AddPlaylistCommand() {
	}

	public function execute(_event:CairngormEvent):void {
		const event:AddPlaylistEvent = _event as AddPlaylistEvent;
		LOG.fine("execute(event=" + event + ")");
		
		AddDelegate.instance.doAdd(Playlist.newDefault(), event.targetFolder);
	}
}
}