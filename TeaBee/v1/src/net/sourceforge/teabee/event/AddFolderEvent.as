package net.sourceforge.teabee.event {

import com.adobe.cairngorm.control.CairngormEvent;

import net.sourceforge.teabee.view.libtree.INodeContainer;

public class AddFolderEvent extends CairngormEvent {
	
	public static const EVENT_ID:String = "addFolder";
	
	public var targetFolder:INodeContainer;
	
	public function AddFolderEvent(targetFolder:INodeContainer=null, bubbles:Boolean=false, cancelable:Boolean=false) {
		super(EVENT_ID, bubbles, cancelable);
		
		this.targetFolder = targetFolder;
	}
	
	public override function toString():String {
		return "AddFolderEvent[targetFolder=" + this.targetFolder + "]";
	}
	
}
}