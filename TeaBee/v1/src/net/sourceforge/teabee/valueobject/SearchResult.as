package net.sourceforge.teabee.valueobject {

import mx.collections.ArrayCollection;
	

public class SearchResult {
	
	private var _title:String;
	private var _url:String;
	private var _duration:uint;
	
	
	public function SearchResult(title:String, url:String, duration:uint) {
		this._title = title;
		this._url = url;
		this._duration = duration;
	}
	
	public function toString():String {
		return "SearchResult[_title="+_title+";_url="+_url+";_duration="+_duration+"]";
	}
	
	public function get title():String {
		return this._title;
	}
	
	public function get url():String {
		return this._url;
	}
	
	public function get duration():uint {
		return this._duration;
	}

}
}