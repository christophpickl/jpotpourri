package net.sourceforge.teabee.valueobject {

import mx.collections.ArrayCollection;
	
[Bindable]
public class SearchResult {
	
	public var title:String;
	public var url:String;
	public var duration:uint;
	public var thumbnail:Thumbnail;
	
	
	public function SearchResult(title:String, url:String, duration:uint, thumbnail:Thumbnail) {
		this.title = title;
		this.url = url;
		this.duration = duration;
		this.thumbnail = thumbnail;
	}
	
	public function toString():String {
		return "SearchResult[title="+title+";url="+url+";duration="+duration+";thumbnail="+thumbnail+"]";
	}

}
}