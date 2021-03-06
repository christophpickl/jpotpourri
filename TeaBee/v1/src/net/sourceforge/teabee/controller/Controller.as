package net.sourceforge.teabee.controller {

import com.adobe.cairngorm.control.FrontController;

import net.sourceforge.teabee.command.AddClipCommand;
import net.sourceforge.teabee.command.AddFolderCommand;
import net.sourceforge.teabee.command.AddPlaylistCommand;
import net.sourceforge.teabee.command.DeleteClipCommand;
import net.sourceforge.teabee.command.DeleteFolderCommand;
import net.sourceforge.teabee.command.DeletePlaylistCommand;
import net.sourceforge.teabee.command.PlayClipCommand;
import net.sourceforge.teabee.command.PlayPlaylistCommand;
import net.sourceforge.teabee.command.ResetLibraryCommand;
import net.sourceforge.teabee.command.SaveLibraryCommand;
import net.sourceforge.teabee.command.SearchSubmitCommand;
import net.sourceforge.teabee.event.AddClipEvent;
import net.sourceforge.teabee.event.AddFolderEvent;
import net.sourceforge.teabee.event.AddPlaylistEvent;
import net.sourceforge.teabee.event.DeleteClipEvent;
import net.sourceforge.teabee.event.DeleteFolderEvent;
import net.sourceforge.teabee.event.DeletePlaylistEvent;
import net.sourceforge.teabee.event.PlayClipEvent;
import net.sourceforge.teabee.event.PlayPlaylistEvent;
import net.sourceforge.teabee.event.ResetLibraryEvent;
import net.sourceforge.teabee.event.SaveLibraryEvent;
import net.sourceforge.teabee.event.SearchSubmitEvent;

public class Controller extends FrontController {
	
	public function Controller() {
		
		// search stuff
		// ------------------
		this.addCommand(SearchSubmitEvent.EVENT_ID, SearchSubmitCommand);
		
		
		// library dao stuff
		// ------------------
		this.addCommand(SaveLibraryEvent.EVENT_ID, SaveLibraryCommand);
		this.addCommand(ResetLibraryEvent.EVENT_ID, ResetLibraryCommand);
		
		
		// play stuff
		// ------------------
		this.addCommand(PlayPlaylistEvent.EVENT_ID, PlayPlaylistCommand);
		this.addCommand(PlayClipEvent.EVENT_ID, PlayClipCommand);
		
		
		// crud stuff
		// ------------------
		this.addCommand(AddFolderEvent.EVENT_ID, AddFolderCommand);
		this.addCommand(DeleteFolderEvent.EVENT_ID, DeleteFolderCommand);
		
		this.addCommand(AddPlaylistEvent.EVENT_ID, AddPlaylistCommand);
		this.addCommand(DeletePlaylistEvent.EVENT_ID, DeletePlaylistCommand);
		
		this.addCommand(AddClipEvent.EVENT_ID, AddClipCommand);
		this.addCommand(DeleteClipEvent.EVENT_ID, DeleteClipCommand);
		
	}
	
}
}