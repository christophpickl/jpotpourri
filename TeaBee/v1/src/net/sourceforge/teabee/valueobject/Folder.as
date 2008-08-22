package net.sourceforge.teabee.valueobject {

import mx.collections.ArrayCollection;

import net.sourceforge.teabee.view.libtree.INodeContainer;

[Bindable]
public class Folder implements INodeContainer {
	
	
	public var title:String;
	
	public var parent:INodeContainer; // TODO be aware, if tree dragndrops items => also adjust parent reference
	
	public var content:ArrayCollection;
	
	
	
	public function Folder(title:String, parent:INodeContainer=null, content:ArrayCollection=null) {
		this.title = title;
		this.parent = parent;
		
		this.content = content == null ? new ArrayCollection() : content;
	}
	
	public static function newDefault():Folder {
		return new Folder("untitled folder");
	}

	public function toString():String {
		return "Folder[title=" + title + ";parent=" + parent + ";content.length=" + content.length + "]";
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
	public function get parentNode():INodeContainer {
		return this.parent;
	}
	
	/**
	 * implements INode
	 */
	public function set parentNode(parentNode:INodeContainer):void {
		this.parent = parentNode;
	}
	
	/**
	 * implements INode
	 */
	public function get children():ArrayCollection {
		return this.content;;
	}
	
}
}