package net.sourceforge.teabee.command {

import com.adobe.cairngorm.commands.ICommand;
import com.adobe.cairngorm.control.CairngormEvent;

import logging.Logger;

import net.sourceforge.teabee.dao.LibraryDao;
import net.sourceforge.teabee.event.SaveLibraryEvent;
import net.sourceforge.teabee.model.Model;

public class SaveLibraryCommand implements ICommand {
	
	private static const LOG:Logger = Logger.getLogger("net.sourceforge.teabee.command.SaveLibraryCommand");
	
	public function SaveLibraryCommand() {
	}

	public function execute(_event:CairngormEvent):void {
		const event:SaveLibraryEvent = _event as SaveLibraryEvent;
		LOG.fine("execute(event=" + event + ")");
		
		LibraryDao.instance.save(Model.instance.library);
	}
	
}
}