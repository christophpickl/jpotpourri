package net.sourceforge.teabee.command {

import com.adobe.cairngorm.commands.ICommand;
import com.adobe.cairngorm.control.CairngormEvent;

import logging.Logger;

import net.sourceforge.teabee.dao.LibraryDao;
import net.sourceforge.teabee.event.ResetLibraryEvent;
import net.sourceforge.teabee.model.Model;
import net.sourceforge.teabee.valueobject.Library;


public class ResetLibraryCommand implements ICommand {
	
	private static const LOG:Logger = Logger.getLogger("net.sourceforge.teabee.command.ResetLibraryCommand");
	
	public function ResetLibraryCommand() {
	}

	public function execute(_event:CairngormEvent):void {
		const event:ResetLibraryEvent = _event as ResetLibraryEvent;
		LOG.fine("execute(event=" + event + ")");
		
		LibraryDao.instance.clear();
		
		const model:Model = Model.instance;
		/* FIXME funktioniert noch nicht, weil tree komische art&weise pseudo-binding mittels listener hat
		   !!! ueberhaupt diese delegate.listeners weg tun, ist gar nicht gut !!!
		model.library = Library.newDefault();
		model.selectedPlaylist = null;
		model.selectedClip = null;
		*/
		
	}
}
}