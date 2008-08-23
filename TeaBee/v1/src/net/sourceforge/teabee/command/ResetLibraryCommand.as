package net.sourceforge.teabee.command {

import com.adobe.cairngorm.commands.ICommand;
import com.adobe.cairngorm.control.CairngormEvent;

import logging.Logger;

import net.sourceforge.teabee.business.ResetLibraryDelegate;
import net.sourceforge.teabee.event.ResetLibraryEvent;


public class ResetLibraryCommand implements ICommand {
	
	private static const LOG:Logger = Logger.getLogger("net.sourceforge.teabee.command.ResetLibraryCommand");
	
	public function ResetLibraryCommand() {
	}

	public function execute(_event:CairngormEvent):void {
		const event:ResetLibraryEvent = _event as ResetLibraryEvent;
		LOG.fine("execute(event=" + event + ")");
		
		ResetLibraryDelegate.reset();
	}
}
}