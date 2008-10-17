package view {

import code.vo.Library;

import logging.Logger;

import mx.controls.Tree;


public class LibraryTree extends Tree {
	
	private static const LOG: Logger = Logger.getLogger("view.LibraryTree");	
	
	public function LibraryTree() {
		this.showRoot = false;
		this.editable = true;
		
		// treeLabel(item: Object):String {
		this.labelFunction = function(item: Object): String {
			return item.title;
			/*
			if(item is Library) {
				return "-LIBRARY-"; // wont be visible to user
			} else {
				return item.title;
			}
			*/
		};
	}
	
	
	
	/*
	private function openNodesUntil(node: Object):void {
		while(node != null) {
			// this.expandChildrenOf(node, true); NO: also expands auf-gleicher-ebene nodes
			this.expandItem(node, true, false);
			node = node.parentNode;
		}
	}
	*/

}
}