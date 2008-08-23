package net.sourceforge.teabee.view.libtree {

import flash.events.KeyboardEvent;
import flash.events.MouseEvent;
import flash.ui.Keyboard;

import logging.Logger;

import mx.controls.TextInput;
import mx.controls.Tree;
import mx.core.DragSource;
import mx.core.UIComponent;
import mx.core.mx_internal;
import mx.events.DataGridEvent;
import mx.events.DragEvent;
import mx.events.ListEvent;
import mx.managers.DragManager;
import mx.utils.ObjectUtil;

import net.sourceforge.teabee.business.TreeChangeProvider;
import net.sourceforge.teabee.event.AddFolderEvent;
import net.sourceforge.teabee.event.AddPlaylistEvent;
import net.sourceforge.teabee.event.DeleteFolderEvent;
import net.sourceforge.teabee.event.DeletePlaylistEvent;
import net.sourceforge.teabee.model.Model;
import net.sourceforge.teabee.valueobject.Folder;
import net.sourceforge.teabee.valueobject.Playlist;
import net.sourceforge.teabee.view.Assets;

// used to access Tree._dropData
use namespace mx_internal;


public class LibraryTree extends Tree {
	
	private static const LOG:Logger = Logger.getLogger("net.sourceforge.teabee.view.LibraryTree");	
	
	/** used to avoid catching key event for node deletion */
	private var _isEditing:Boolean = false;
	
	
	
	public function LibraryTree() {
		
		this.showRoot = false;
		this.editable = true;
		
		this.iconFunction = this.treeIconFunction;
		
		
		this.doubleClickEnabled = true;
		this.addEventListener(MouseEvent.CLICK, onClick);
		this.addEventListener(MouseEvent.DOUBLE_CLICK, onDoubleClick);
		
		
		this.dragEnabled = true;
		this.dragMoveEnabled = true;
		this.dropEnabled = true;
		this.addEventListener(DragEvent.DRAG_ENTER, onDragEnter);
		this.addEventListener(DragEvent.DRAG_DROP, onDragDrop);
		
		this.addEventListener(ListEvent.CHANGE, onChange);
		
		this.addEventListener(KeyboardEvent.KEY_UP, onKeyUp);
				
		this.addEventListener(ListEvent.ITEM_EDIT_BEGIN, onItemEditBegin);
		this.addEventListener(ListEvent.ITEM_EDIT_END, onItemEditEnd);
		
		
		// FIXME sollte per binding irgendwie automatisch im setter tree.invalidate werden!!!
		//       weil: beim ResetLibrary wird noch nicht invalidated!!!
		TreeChangeProvider.instance.addAddListener(didAdd);
		TreeChangeProvider.instance.addResetListener(didReset);
		
	}
	
	private function onItemEditBegin(event:DataGridEvent):void {
		// to take care of editing all alone: event.preventDefault();
		// const node:INode = event.itemRenderer.data as INode;
		
		this._isEditing = true;
	}
	
	private function onItemEditEnd(event:DataGridEvent):void {
		const node:INode = event.itemRenderer.data as INode;
		const editor:TextInput = this.itemEditorInstance as TextInput;
		if(editor.text.length == 0) {
			/// prevent setting title to empty string
			event.preventDefault();
		}
		this._isEditing = false;
	}
	
	private function onKeyUp(event:KeyboardEvent):void {
		if(this.selectedItem != null &&
		   this._isEditing == false && // only, if not currently not editing a label
		   (event.keyCode == Keyboard.DELETE || event.keyCode == Keyboard.BACKSPACE) ) {
			LOG.fine("User hit delete key for item [" + this.selectedItem + "].");
			
			this.doDelete();
		}
	}
	
	private function doDelete():void {
		if(this.selectedItem is Playlist) {
			new DeletePlaylistEvent(this.selectedItem as Playlist).dispatch();
		} else if(this.selectedItem is Folder) {
			new DeleteFolderEvent(this.selectedItem as Folder).dispatch();
		} else {
			throw new Error("Unhandled library tree item: " + this.selectedItem);
		}
		
		this.invalidateDisplayList();
	}
	
	
	private function openNodesUntil(node:INode):void {
		while(node != null) {
			// this.expandChildrenOf(node, true); NO: also expands auf-gleicher-ebene nodes
			this.expandItem(node, true, false);
			node = node.parentNode;
		}
		
	}
			
	private function treeIconFunction(item:Object): Class {
		if(item is Playlist) {
			return Assets.TreePlaylistIcon;
		} else if(item is Folder) {
			return Assets.TreeFolderIcon;
		} else {
			throw new Error("Unhandled tree object: " + item);
		}
	}
			
	private function onChange(event:ListEvent):void {
		var item:Object = this.selectedItem;
		LOG.finer("onChange() this.selectedItem=" + item);
		if(item is Playlist) {
			Model.instance.selectedPlaylist = item as Playlist;
		} else {
			Model.instance.selectedPlaylist = null;
		}
		
		Model.instance.selectedClip = null;
	}
			
	/* ****************************************************************************************************** */
	//    PSEUDO DATA DESCRIPTOR CHANGES
	/* ****************************************************************************************************** */
			
	private function didAdd(node:INode):void {
		LOG.finer("didAdd(node=" + node + ")");
		this.invalidateList();
		
		this.openNodesUntil(node);
		this.selectedItem = node;
		 
		this.editable = true;
		this.editedItemPosition = {columnIndex: 0, rowIndex: this.selectedIndex}; // TODO duplicate code
	}
	
	private function didReset():void {
		this.invalidateList();
	}
	
	/* ****************************************************************************************************** */
	//    ADD
	/* ****************************************************************************************************** */
	
	
	public function doAddFolder():void {
		LOG.fine("doAddFolder()");
		new AddFolderEvent(this.getDefaultAddTarget()).dispatch();
	}
	
	public function doAddPlaylist():void {
		LOG.fine("doAddPlaylist()");
		new AddPlaylistEvent(this.getDefaultAddTarget()).dispatch();
	}
	
	private function getDefaultAddTarget():INodeContainer {
		var defaultTarget:INode = null;
		
		if(this.selectedItem != null) {
			var selectedNode:INode = this.selectedItem as INode;
			if(selectedNode.children != null) { // its not a leaf
				defaultTarget = selectedNode;
			} else {
				defaultTarget = selectedNode.parentNode; // its a leaf, get parent
			}
		}
		
		LOG.finer("getDefaultAddTarget() returns: " + defaultTarget);
		return defaultTarget as INodeContainer;
	}
	
	/* ****************************************************************************************************** */
	//    JUST ENABLE DOUBLECLICK
	/* ****************************************************************************************************** */
	
	private function onClick(event:MouseEvent):void {
		// LOG.finest("onTreeClick()");
		this.editable = false;
	}
	
	private function onDoubleClick(event:MouseEvent):void {
		LOG.finer("onTreeDoubleClick()");
		this.editable = true;
		this.editedItemPosition = {columnIndex: 0, rowIndex: this.selectedIndex}; // TODO duplicate code
	}
			
	/* ****************************************************************************************************** */
	//    DRAG'N'DROP
	/* ****************************************************************************************************** */
	
	/** this function validates the drop */
	private function onDragEnter(event:DragEvent):void {
		LOG.finer("onDragEnter(event="+event+")");
		
        if (event.dragInitiator is LibraryTree) {
        	// accept the drop from yourself
        	DragManager.acceptDragDrop(UIComponent(event.currentTarget));
        } // TODO else if from search result or from playlist table
	}
	
	/** this function adds new items to the list */
	private function onDragDrop(event:DragEvent):void {
		LOG.finest("onDragDrop(event="+event+")");
		
		
		var ds:DragSource = event.dragSource;
		if(event.dragInitiator is LibraryTree && ds.hasFormat("treeItems")) {
			var treeItems:Array = ds.dataForFormat("treeItems") as Array;
			LOG.finest("dropped treeItems.length="+treeItems.length);
			if(treeItems.length != 1) {
				throw new Error("treeItems.length != 1 but " + treeItems.length + "! " + ObjectUtil.toString(treeItems));
			}
			
			var node:INode = treeItems[0] as INode;
			LOG.finer("dropping node is: " + node);
			var newParent:INodeContainer;
			
			var rawParent:Object = this._dropData.parent;
			if(rawParent == null) {
				newParent = Model.instance.library;
			} else {
				newParent = rawParent as INodeContainer;
			}
			LOG.finer("new parent will be: " + newParent);
			node.parentNode = newParent;
			
		} else {
			throw new Error("drag source has no 'treeItems' format! " + ObjectUtil.toString(ds));
		}
		
	}
	
}
}