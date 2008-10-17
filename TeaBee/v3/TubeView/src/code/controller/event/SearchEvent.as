package code.controller.event {

import com.adobe.cairngorm.control.CairngormEvent;
	

public class SearchEvent extends CairngormEvent {
	
	public static const EVENT_ID:String = "search";
	
	public var searchString: String;
	
	public function SearchEvent(searchString: String, bubbles: Boolean=false, cancelable: Boolean=false) {
		super(EVENT_ID, bubbles, cancelable);
		
		this.searchString = searchString;
	}
	
	public override function toString(): String {
		return "SearchEvent[searchString=" + this.searchString + "]";
	}

}
}