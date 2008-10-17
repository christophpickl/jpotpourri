package code.view {

import code.model.Model;
import code.model.vo.Playlist;

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
		
		this.addEventListener(ListEvent.CHANGE, this.onChange);
		
		this.labelFunction = function(item: Object): String {
			return item.title;
		};
	}
	
	private function onItemEditEnd(event: ListEvent): void {
		var input: TextInput = this.itemEditorInstance as TextInput;
		this.selectedItem.title = input.text;
	}
	private function onChange(event: ListEvent): void {
		if(this.selectedItem is Playlist) {
			Model.instance.playlist = this.selectedItem as Playlist;
		}
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