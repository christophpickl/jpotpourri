package code.controller {

import code.controller.command.AddFolderCommand;
import code.controller.command.AddPlaylistCommand;
import code.controller.command.SearchCommand;
import code.controller.event.AddFolderEvent;
import code.controller.event.AddPlaylistEvent;
import code.controller.event.SearchEvent;

import com.adobe.cairngorm.control.FrontController;


public class MainFrontController extends FrontController {
	
	public function MainFrontController() {
		
		this.addCommand(AddPlaylistEvent.EVENT_ID, AddPlaylistCommand);
		this.addCommand(AddFolderEvent.EVENT_ID, AddFolderCommand);
		
		this.addCommand(SearchEvent.EVENT_ID, SearchCommand);
		
		
	}
	
}
}