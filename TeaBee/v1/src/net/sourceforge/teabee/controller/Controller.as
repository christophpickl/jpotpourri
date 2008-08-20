package net.sourceforge.teabee.controller {

import com.adobe.cairngorm.control.FrontController;

import net.sourceforge.teabee.command.SearchSubmitCommand;
import net.sourceforge.teabee.event.SearchSubmitEvent;

public class Controller extends FrontController {
	
	public function Controller() {
		
		this.addCommand(SearchSubmitEvent.EVENT_ID, SearchSubmitCommand);
		
	}
	
}
}