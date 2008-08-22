package net.sourceforge.teabee.event {

import com.adobe.cairngorm.control.CairngormEvent;

public class ResetLibraryEvent extends CairngormEvent {
	
	public static const EVENT_ID:String = "resetLibrary";
	
	public function ResetLibraryEvent(bubbles:Boolean=false, cancelable:Boolean=false) {
		super(EVENT_ID, bubbles, cancelable);
	}
	
	public override function toString():String {
		return "ResetLibraryEvent[]";
	}
	
}
}