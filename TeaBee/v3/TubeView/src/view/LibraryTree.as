package view {

import logging.Logger;

import mx.controls.TextInput;
import mx.controls.Tree;
import mx.events.ListEvent;


public class LibraryTree extends Tree {
	
	private static const LOG: Logger = Logger.getLogger("view.LibraryTree");	
	
	public function LibraryTree() {
		this.showRoot = false;
		this.editable = true;
		
		this.addEventListener(ListEvent.ITEM_EDIT_END, this.onItemEditEnd);
		
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
	
	private function onItemEditEnd(event: ListEvent): void {
		var input: TextInput = this.itemEditorInstance as TextInput;
		this.selectedItem.title = input.text;
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