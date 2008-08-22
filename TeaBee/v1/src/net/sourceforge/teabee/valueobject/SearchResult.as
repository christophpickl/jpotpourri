package net.sourceforge.teabee.valueobject {

import mx.collections.ArrayCollection;
	

public class SearchResult {
	
	private var _title:String;
	private var _url:String;
	private var _duration:uint;
	private var _thumbnail:Thumbnail;
	
	
	public function SearchResult(title:String, url:String, duration:uint, thumbnail:Thumbnail) {
		this._title = title;
		this._url = url;
		this._duration = duration;
		this._thumbnail = thumbnail;
	}
	
	public function toString():String {
		return "SearchResult[_title="+_title+";_url="+_url+";_duration="+_duration+";_thumbnail="+_thumbnail+"]";
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
	
	public function get thumbnail():Thumbnail {
		return this._thumbnail;
	}

}
}