package net.sourceforge.teabee.command {

import com.adobe.cairngorm.commands.ICommand;
import com.adobe.cairngorm.control.CairngormEvent;

import logging.Logger;

import net.sourceforge.teabee.business.AddDelegate;
import net.sourceforge.teabee.event.AddClipEvent;

public class AddClipCommand implements ICommand {
	
	private static const LOG:Logger = Logger.getLogger("net.sourceforge.teabee.command.AddClipCommand");
	
	public function AddClipCommand() {
	}

	public function execute(_event:CairngormEvent):void {
		const event:AddClipEvent = _event as AddClipEvent;
		LOG.fine("execute(event=" + event + ")");
		
		AddDelegate.instance.doAddClip(event.playlist, event.searchResult);
	}
}
}