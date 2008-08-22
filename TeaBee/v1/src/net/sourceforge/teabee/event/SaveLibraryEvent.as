package net.sourceforge.teabee.event {

import com.adobe.cairngorm.control.CairngormEvent;

public class SaveLibraryEvent extends CairngormEvent {
	
	public static const EVENT_ID:String = "saveLibrary";
	
	public function SaveLibraryEvent(bubbles:Boolean=false, cancelable:Boolean=false) {
		super(EVENT_ID, bubbles, cancelable);
	}
	
	public override function toString():String {
		return "SaveLibraryEvent[]";
	}
	
}
}