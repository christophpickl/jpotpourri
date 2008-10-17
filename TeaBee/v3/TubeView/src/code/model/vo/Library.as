package code.model.vo {

import mx.collections.ArrayCollection;
	

public class Library {
	
	[Bindable] public var rootFolder: Folder; 
	
	public function Library(pRootFolder: Folder) {
		if(pRootFolder == null) throw new Error("pRootFolder == null");
		this.rootFolder = pRootFolder;
	}
	
	public static function newDefault():Library {
		const folder: Folder = Folder.newDefault();
		
		folder.content.addItem(Playlist.newDefault());
		
		return new Library(folder);
	}

}
}