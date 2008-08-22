package net.sourceforge.teabee.view {

[Bindable]
public class Assets {
	
	[Embed("assets/img/icons/playlist.png")]
    public static var TreePlaylistIcon:Class;
    
	[Embed("assets/img/icons/folder.png")]
    public static  var TreeFolderIcon:Class;
    
	//[Embed("assets/img/icons/folder_opened.png")]
    //private var TreeFolderOpenedIcon:Class;
    
	public function Assets() {
		throw new Error("Assets instantiation not allowed!");
	}

}
}

