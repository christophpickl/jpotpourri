package code.vo {

import mx.collections.ArrayCollection;


[Bindable]
public class Playlist {
	
	public var title: String;
	
	public var videos: ArrayCollection = new ArrayCollection();
	
	public function Playlist(pTitle: String, pVideos: ArrayCollection) {
		if(pTitle == null) throw new Error("pTitle == null");
		if(pVideos == null) throw new Error("pVideos == null");
		
		this.title = pTitle;
		this.videos = pVideos;
	}

	public static function newDefault():Playlist {
		return new Playlist("untitled playlist", new ArrayCollection());
	}

	public function toString():String {
		return "Playlist[title=" + this.title + ";videos.length=" + this.videos.length + "]";
	}


}
}