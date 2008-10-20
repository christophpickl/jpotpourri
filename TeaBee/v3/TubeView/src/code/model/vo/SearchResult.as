package code.model.vo {

import mx.collections.ArrayCollection;
	
[Bindable]
public class SearchResult {
	
	public var clipId: String;
	
	public var title: String;
	
	public var url: String;
	
	public var duration: uint;
	
	public var thumbnail: Thumbnail;
	
	
	public function SearchResult(clipId: String, title:String, url:String, duration:uint, thumbnail:Thumbnail) {
		this.clipId = clipId;
		this.title = title;
		this.url = url;
		this.duration = duration;
		this.thumbnail = thumbnail;
	}
	
	public function newClip(): Clip {
		return new Clip(this.clipId, this.title, this.url, this.duration, this.thumbnail);
	}
	
	public function toString():String {
		return "SearchResult[clipId="+clipId+";title="+title+";url="+url+";duration="+duration+";thumbnail="+thumbnail+"]";
	}

}
}