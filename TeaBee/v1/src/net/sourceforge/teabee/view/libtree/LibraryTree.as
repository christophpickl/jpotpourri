package net.sourceforge.teabee.view.libtree {

import logging.Logger;

import mx.controls.Tree;

import net.sourceforge.teabee.model.Model;


public class LibraryTree extends Tree {
	
	private static const LOG:Logger = Logger.getLogger("net.sourceforge.teabee.view.LibraryTree");
	
	
	public function LibraryTree() {
		this.dataProvider = Model.instance.library;
		
		this.showRoot = false;
		this.editable = true;
		
		this.dragEnabled = true;
		this.dragMoveEnabled = true;
		this.dropEnabled = true;
		
		this.doubleClickEnabled = true;
		
	}
	
	public function openNodesUntil(node:INode):void {
		
		while(node != null) {
			// this.tree.expandChildrenOf(node, true); NO: also expands auf-gleicher-ebene nodes
			this.expandItem(node, true, false);
			node = node.parentNode;
		}
		
	}
	
/*

click="onTreeClick()" doubleClick="onTreeDoubleClick()" doubleClickEnabled="true"
*/
	
}
}