package code.model.vo {

import mx.collections.ArrayCollection;


[Bindable]
public class Playlist extends Item {
	
	public var playlistId: int;
	
	private var _clips: ArrayCollection = new ArrayCollection();
	
	
	public function Playlist(pTitle: String, pClips: ArrayCollection) {
		super(pTitle);
		if(pClips == null) throw new Error("pClips == null");
		
		this.clips = pClips;
	}

	public static function newDefault():Playlist {
		return new Playlist("untitled playlist", new ArrayCollection());
	}

	public function get clips(): ArrayCollection {
		return this._clips;
	}
	
	public function set clips(pClips: ArrayCollection): void {
		if(pClips == null) throw new Error("pClips == null");
		this._clips = pClips; 
	}

	public function toString():String {
		return "Playlist["+this.toInnerString()+";playlistId=" + this.playlistId + ";clips.length=" + this._clips.length + "]";
	}


}
}