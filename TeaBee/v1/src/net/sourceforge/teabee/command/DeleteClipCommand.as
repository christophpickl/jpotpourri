package net.sourceforge.teabee.command {

import com.adobe.cairngorm.commands.ICommand;
import com.adobe.cairngorm.control.CairngormEvent;

import logging.Logger;

import net.sourceforge.teabee.event.DeleteClipEvent;
import net.sourceforge.teabee.model.Model;
import net.sourceforge.teabee.valueobject.Clip;
import net.sourceforge.teabee.valueobject.Playlist;

public class DeleteClipCommand implements ICommand {
	
	private static const LOG:Logger = Logger.getLogger("net.sourceforge.teabee.command.DeleteClipCommand");
	
	public function DeleteClipCommand() {
	}

	public function execute(_event:CairngormEvent):void {
		const event:DeleteClipEvent = _event as DeleteClipEvent;
		LOG.fine("execute(event=" + event + ")");
		
		const playlist:Playlist = event.playlist;
		const clip:Clip = event.clip;
		
		const clipIndex:int = playlist.clips.getItemIndex(clip);
		if(clipIndex < 0) {
			throw new Error("Could not get item index ["+clipIndex+"] of clip ["+clip+"] by playlist ["+playlist+"]!");
		}
		playlist.clips.removeItemAt(clipIndex);
		
		if(Model.instance.selectedClip == clip) {
			Model.instance.selectedClip = null;
		}
		
	}
}
}