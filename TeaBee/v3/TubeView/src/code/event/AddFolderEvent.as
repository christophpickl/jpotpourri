package code.event {

import code.vo.Folder;

import com.adobe.cairngorm.control.CairngormEvent;


public class AddFolderEvent extends CairngormEvent {
	
	public static const EVENT_ID:String = "addFolder";
	
	public var folder: Folder;
	
	public var targetFolder: Folder;
	
	public function AddFolderEvent(folder: Folder, targetFolder: Folder=null, bubbles: Boolean=false, cancelable: Boolean=false) {
		super(EVENT_ID, bubbles, cancelable);
		
		this.folder = folder;
		this.targetFolder = targetFolder;
	}
	
	public override function toString(): String {
		return "AddFolderEvent[folder=" + this.folder + ";]";
	}

}
}