package code.controller.event {

import code.model.vo.Folder;
import code.model.vo.Playlist;

import com.adobe.cairngorm.control.CairngormEvent;


public class AddPlaylistEvent extends CairngormEvent {
	
	public static const EVENT_ID:String = "addPlaylist";
	
	public var playlist: Playlist;
	
	public var targetFolder: Folder;
	
	public function AddPlaylistEvent(playlist: Playlist, targetFolder: Folder=null, bubbles: Boolean=false, cancelable: Boolean=false) {
		super(EVENT_ID, bubbles, cancelable);
		
		this.playlist = playlist;
		this.targetFolder = targetFolder;
	}
	
	public override function toString(): String {
		return "AddClipEvent[playlist=" + this.playlist + ";]";
	}

}
}