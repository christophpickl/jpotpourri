package net.sourceforge.teabee.event {

import com.adobe.cairngorm.control.CairngormEvent;

import net.sourceforge.teabee.valueobject.Playlist;

public class DeletePlaylistEvent extends CairngormEvent {
	
	public static const EVENT_ID:String = "deletePlaylist";
	
	public var playlist:Playlist;
	
	public function DeletePlaylistEvent(playlist:Playlist, bubbles:Boolean=false, cancelable:Boolean=false) {
		super(EVENT_ID, bubbles, cancelable);
		
		this.playlist = playlist;
	}
	
	public override function toString():String {
		return "DeletePlaylistEvent[playlist=" + this.playlist + "]";
	}
	
	
}
}