package net.sourceforge.teabee.command {

import com.adobe.cairngorm.commands.ICommand;
import com.adobe.cairngorm.control.CairngormEvent;

import logging.Logger;

import net.sourceforge.teabee.event.PlayClipEvent;

public class PlayClipCommand implements ICommand {
	
	private static const LOG:Logger = Logger.getLogger("net.sourceforge.teabee.command.PlayClipCommand");
	
	public function PlayClipCommand() {
		
	}

	public function execute(_event:CairngormEvent):void {
		const event:PlayClipEvent = _event as PlayClipEvent;
		LOG.fine("execute(event=" + event + ")");
		
		
	}
	
}
}