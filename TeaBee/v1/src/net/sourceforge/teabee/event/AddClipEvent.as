package net.sourceforge.teabee.event {

import com.adobe.cairngorm.control.CairngormEvent;

import net.sourceforge.teabee.valueobject.Playlist;
import net.sourceforge.teabee.valueobject.SearchResult;
import net.sourceforge.teabee.view.libtree.INodeContainer;

public class AddClipEvent extends CairngormEvent {
	
	public static const EVENT_ID:String = "addClip";
	
	public var playlist:Playlist;
	
	public var searchResult:SearchResult;
	
	public function AddClipEvent(playlist:Playlist, searchResult:SearchResult, bubbles:Boolean=false, cancelable:Boolean=false) {
		super(EVENT_ID, bubbles, cancelable);
		
		this.playlist = playlist;
		this.searchResult = searchResult;
	}
	
	public override function toString():String {
		return "AddClipEvent[playlist=" + playlist + ";searchResult=" + searchResult + "]";
	}
	
}
}