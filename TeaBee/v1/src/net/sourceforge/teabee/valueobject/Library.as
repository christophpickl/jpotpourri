package net.sourceforge.teabee.valueobject {

import mx.collections.ArrayCollection;

import net.sourceforge.teabee.view.libtree.INodeContainer;
	

[Bindable]
public class Library implements INodeContainer {
	
	private static const TITLE:String = "Library";

	/** ArrayCollection<INode> */
	public var content:ArrayCollection;
	
	
	
	public function Library(content:ArrayCollection) {
		this.content = content;
	}
	
	public static function newDefault():Library {
		const lists:ArrayCollection = new ArrayCollection();
		const library:Library = new Library(lists);
		
		var defaultPlaylist:Playlist = Playlist.newDefault();
		defaultPlaylist.parentNode = library;
		
		lists.addItem(defaultPlaylist);
		
		return library;
	}
	
	public function toString():String {
		return "Library[content.length=" + content.length + "]";
	}
	
	/**
	 * implements INode
	 */
	public function get label():String {
		return TITLE;
	}
	
	/**
	 * implements INode
	 */
	public function set label(label:String):void {
		throw new Error("Cannot set label for Library object!"); // TODO Library erst gar nicht so ein interface impln lassen!
	}
	
	/**
	 * implements INode
	 */
	public function get children():ArrayCollection {
		return this.content;
	}
	
	/**
	 * implements INode
	 */
	public function get parentNode():INodeContainer {
		return null;
	}
	
	/**
	 * implements INode
	 */
	public function set parentNode(parentNode:INodeContainer):void {
		throw new Error("Cannot set parentNode for Library object!"); // TODO Library erst gar nicht so ein interface impln lassen!
	}

}
}
