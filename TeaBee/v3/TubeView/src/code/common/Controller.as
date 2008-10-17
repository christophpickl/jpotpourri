package code.common {

import code.controller.command.AddFolderCommand;
import code.controller.command.AddPlaylistCommand;
import code.controller.event.AddFolderEvent;
import code.controller.event.AddPlaylistEvent;

import com.adobe.cairngorm.control.FrontController;
	

public class Controller extends FrontController {
	
	public function Controller() {
		
		// search stuff
		// ------------------
		this.addCommand(AddPlaylistEvent.EVENT_ID, AddPlaylistCommand);
		this.addCommand(AddFolderEvent.EVENT_ID, AddFolderCommand);
		
		
	}
	
}
}