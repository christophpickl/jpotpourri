package code.common {

import code.command.AddFolderCommand;
import code.command.AddPlaylistCommand;
import code.event.AddFolderEvent;
import code.event.AddPlaylistEvent;

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