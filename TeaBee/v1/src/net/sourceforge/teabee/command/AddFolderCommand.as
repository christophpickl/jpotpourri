package net.sourceforge.teabee.command {

import com.adobe.cairngorm.commands.ICommand;
import com.adobe.cairngorm.control.CairngormEvent;

import logging.Logger;

import net.sourceforge.teabee.business.AddDelegate;
import net.sourceforge.teabee.event.AddFolderEvent;
import net.sourceforge.teabee.valueobject.Folder;

public class AddFolderCommand implements ICommand {
	
	private static const LOG:Logger = Logger.getLogger("net.sourceforge.teabee.command.AddFolderCommand");
	
	public function AddFolderCommand() {
	}

	public function execute(_event:CairngormEvent):void {
		const event:AddFolderEvent = _event as AddFolderEvent;
		LOG.fine("execute(event=" + event + ")");
		
		AddDelegate.instance.doAdd(Folder.newDefault(), event.targetFolder);
	}
}
}