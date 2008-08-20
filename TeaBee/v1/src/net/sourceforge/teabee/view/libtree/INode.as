package net.sourceforge.teabee.view.libtree {

import mx.collections.ArrayCollection;
	

public interface INode {
	
	function get label():String;
	
	function set label(label:String):void;
	
	function get parentNode():INodeContainer;
	
	function set parentNode(parentNode:INodeContainer):void;
	
	// unfortunately, abstract classes are NOT supported by AS3 !!!
	function get children():ArrayCollection;
	
}
}