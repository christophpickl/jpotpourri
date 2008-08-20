package net.sourceforge.teabee.event {

import com.adobe.cairngorm.control.CairngormEvent;

public class SearchSubmitEvent extends CairngormEvent {
	
	public static const EVENT_ID:String = "searchSubmit";
	
	public var searchTerm:String;
	
	public function SearchSubmitEvent(searchTerm:String, bubbles:Boolean=false, cancelable:Boolean=false) {
		super(EVENT_ID, bubbles, cancelable);
		
		this.searchTerm = searchTerm;
	}
	
	public override function toString():String {
		return "SearchSubmitEvent[searchTerm=" + this.searchTerm + "]";
	}
}
}