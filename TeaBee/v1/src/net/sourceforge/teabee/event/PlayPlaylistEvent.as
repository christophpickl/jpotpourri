package net.sourceforge.teabee.event {

import com.adobe.cairngorm.control.CairngormEvent;

import net.sourceforge.teabee.valueobject.Clip;
import net.sourceforge.teabee.valueobject.Playlist;

public class PlayPlaylistEvent extends CairngormEvent {
	
	public var playlist:Playlist;
	public var clip:Clip;
	
	public static const EVENT_ID:String = "playPlaylist";
	
	public function PlayPlaylistEvent(playlist:Playlist, clip:Clip, bubbles:Boolean=false, cancelable:Boolean=false) {
		super(EVENT_ID, bubbles, cancelable);
		
		this.playlist = playlist;
		this.clip = clip;
	}
	
	public override function toString():String {
		return "PlayPlaylistEvent[playlist="+playlist+";clip="+clip+"]";
	}
	
}
}