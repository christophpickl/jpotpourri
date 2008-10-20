package code.model.vo {

import mx.collections.ArrayCollection;

[Bindable]
public class Folder extends Item {
	
	public var folderId: int;
	
	/** ArrayCollection<Folder|Playlist> */
	public var content: ArrayCollection;
	
	
	public function Folder(pTitle: String, pContent: ArrayCollection) {
		super(pTitle);
		if(pContent == null) throw new Error("pContent == null");
		
		this.content = pContent;
	}
	
	public static function newDefault(): Folder {
		return new Folder("untitled folder", new ArrayCollection());
	}
	
	public function toString():String {
		return "Folder["+this.toInnerString()+";folderId=" + this.folderId + ";content.length=" + this.content.length + "]";
	}

	/** workaround method to get this displayed as a container item in tree component*/
	public function get children(): ArrayCollection {
		return this.content;
	}
}
}