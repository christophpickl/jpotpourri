package net.sourceforge.teabee.command {

import com.adobe.cairngorm.commands.ICommand;
import com.adobe.cairngorm.control.CairngormEvent;

import logging.Logger;

import net.sourceforge.teabee.business.DeleteDelegate;
import net.sourceforge.teabee.event.DeleteFolderEvent;

public class DeleteFolderCommand implements ICommand {
	
	private static const LOG:Logger = Logger.getLogger("net.sourceforge.teabee.command.DeleteFolderCommand");
	
	public function DeleteFolderCommand() {
	}

	public function execute(_event:CairngormEvent):void {
		const event:DeleteFolderEvent = _event as DeleteFolderEvent;
		LOG.fine("execute(event=" + event + ")");
		
		DeleteDelegate.instance.doDeleteFolder(event.folder);
	}
	
}
}