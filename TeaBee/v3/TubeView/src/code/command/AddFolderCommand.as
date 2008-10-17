package code.command {

import code.event.AddFolderEvent;

import com.adobe.cairngorm.commands.ICommand;
import com.adobe.cairngorm.control.CairngormEvent;

import logging.Logger;
	

public class AddFolderCommand implements ICommand {
	
	private static const LOG: Logger = Logger.getLogger("code.command.AddFolderCommand");
	
	
	public function AddFolderCommand() {
	}

	public function execute(_event: CairngormEvent):void {
		LOG.fine("execute(event=" + _event + ")");
		const event:AddFolderEvent = _event as AddFolderEvent;
		
		AddDelegate.addItem(event.folder, event.targetFolder);
		
	}
	
}
}