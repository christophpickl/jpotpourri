package code.controller.command {

import code.common.Model;
import code.controller.event.AddPlaylistEvent;
import code.model.vo.Folder;

import com.adobe.cairngorm.commands.ICommand;
import com.adobe.cairngorm.control.CairngormEvent;

import comp.FrameLibrary;

import logging.Logger;
	

public class AddPlaylistCommand implements ICommand {
	
	
	private static const LOG: Logger = Logger.getLogger("code.command.AddPlaylistCommand");
	
	public function AddPlaylistCommand() {
	}

	public function execute(_event: CairngormEvent):void {
		const event:AddPlaylistEvent = _event as AddPlaylistEvent;
		LOG.fine("execute(event=" + event + ")");
		
		AddDelegate.addItem(event.playlist, event.targetFolder);
		
	}
	
}
}