package net.sourceforge.teabee.event {

import com.adobe.cairngorm.control.CairngormEvent;

import net.sourceforge.teabee.valueobject.Folder;

public class DeleteFolderEvent extends CairngormEvent {
	
	public static const EVENT_ID:String = "deleteFolder";
	
	public var folder:Folder;
	
	public function DeleteFolderEvent(folder:Folder, bubbles:Boolean=false, cancelable:Boolean=false) {
		super(EVENT_ID, bubbles, cancelable);
		
		this.folder = folder;
	}
	
	public override function toString():String {
		return "DeleteFolderEvent[folder=" + this.folder + "]";
	}
	
}
}