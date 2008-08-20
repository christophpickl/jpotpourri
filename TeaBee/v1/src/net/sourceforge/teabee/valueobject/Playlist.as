package net.sourceforge.teabee.valueobject {

import mx.collections.ArrayCollection;

import net.sourceforge.teabee.view.libtree.INode;
import net.sourceforge.teabee.view.libtree.INodeContainer;

[Bindable]
public class Playlist implements INode {
	
	public var title:String;
	
	public var parent:INodeContainer; // TODO be aware, if tree dragndrops items => also adjust parent reference
	
	public var clips:ArrayCollection = new ArrayCollection();
	
	
	public function Playlist(title:String, parent:INodeContainer=null) {
		this.title = title;
		this.parent = parent;
	}


	public function toString():String {
		return "Playlist[title=" + title + ";parent=" + parent + "]";
	}

	/**
	 * implements INode
	 */
	public function get label():String {
		return this.title;
	}
	
	/**
	 * implements INode
	 */
	public function set label(label:String):void {
		this.title = label;
	}
	
	/**
	 * implements INode
	 */
	public function get children():ArrayCollection {
		return null;
	}
	
	/**
	 * implements INode
	 */
	public function get parentNode():INodeContainer {
		return this.parent;
	}
	
	/**
	 * implements INode
	 */
	public function set parentNode(parentNode:INodeContainer):void {
		this.parent = parentNode;
	}
	
}
}