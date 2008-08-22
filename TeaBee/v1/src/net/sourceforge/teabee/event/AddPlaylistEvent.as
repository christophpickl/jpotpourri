package net.sourceforge.teabee.event {

import com.adobe.cairngorm.control.CairngormEvent;

import net.sourceforge.teabee.view.libtree.INodeContainer;

public class AddPlaylistEvent extends CairngormEvent {
	
	public static const EVENT_ID:String = "addPlaylist";
	
	public var targetFolder:INodeContainer;
	
	public function AddPlaylistEvent(targetFolder:INodeContainer=null, bubbles:Boolean=false, cancelable:Boolean=false) {
		super(EVENT_ID, bubbles, cancelable);
		
		this.targetFolder = targetFolder;
	}
	
	public override function toString():String {
		return "AddPlaylistEvent[searchTerm=" + this.targetFolder + "]";
	}
	
}
}