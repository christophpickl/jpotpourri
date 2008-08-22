package net.sourceforge.teabee.event {

import com.adobe.cairngorm.control.CairngormEvent;

import net.sourceforge.teabee.valueobject.Clip;
import net.sourceforge.teabee.valueobject.Folder;
import net.sourceforge.teabee.valueobject.Playlist;

public class DeleteClipEvent extends CairngormEvent {
	
	public static const EVENT_ID:String = "deleteClip";
	
	public var playlist:Playlist;
	public var clip:Clip;
	
	public function DeleteClipEvent(playlist:Playlist, clip:Clip, bubbles:Boolean=false, cancelable:Boolean=false) {
		super(EVENT_ID, bubbles, cancelable);
		
		this.playlist = playlist;
		this.clip = clip;
	}
	
	public override function toString():String {
		return "DeleteClipEvent[playlist=" + playlist + ";clip=" + clip + "]";
	}
	
}
}