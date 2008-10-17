package code.vo {

import mx.collections.ArrayCollection;

[Bindable]
public class Folder {
	
	public var title: String;
	
	/** ArrayCollection<Folder | Playlist> */
	public var content: ArrayCollection;
	
	public function Folder(pTitle: String, pContent: ArrayCollection) {
		if(pTitle == null) throw new Error("pTitle == null");
		if(pContent == null) throw new Error("pContent == null");
		
		this.title = pTitle;
		this.content = pContent;
	}
	
	public static function newDefault(): Folder {
		return new Folder("untitled folder", new ArrayCollection());
	}
	
	public function toString():String {
		return "Folder[title=" + this.title + ";content.length=" + this.content.length + "]";
	}

	/** workaround method to get this displayed as a container item in tree component*/
	public function get children(): ArrayCollection {
		return this.content;
	}
}
}