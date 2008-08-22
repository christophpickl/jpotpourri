package net.sourceforge.teabee.view.libtree {

import flash.events.MouseEvent;

import logging.Logger;

import mx.controls.Tree;
import mx.core.DragSource;
import mx.core.UIComponent;
import mx.core.mx_internal;
import mx.events.DragEvent;
import mx.events.ListEvent;
import mx.managers.DragManager;
import mx.utils.ObjectUtil;

import net.sourceforge.teabee.model.Model;
import net.sourceforge.teabee.valueobject.Folder;
import net.sourceforge.teabee.valueobject.Playlist;
import net.sourceforge.teabee.view.Assets;
use namespace mx_internal;


public class LibraryTree extends Tree {
	
	private static const LOG:Logger = Logger.getLogger("net.sourceforge.teabee.view.LibraryTree");	
	
	
	public function LibraryTree() {
		this.dataProvider = Model.instance.library;
		
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
	}
	
	
	private function openNodesUntil(node:INode):void {
		while(node != null) {
			// this.tree.expandChildrenOf(node, true); NO: also expands auf-gleicher-ebene nodes
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
		LOG.finer("onChange() this.tree.selectedItem=" + item);
		if(item is Playlist) {
			Model.instance.selectedPlaylist = item as Playlist;
		} else {
			Model.instance.selectedPlaylist = null;
		}
		
	}
			
	/* ****************************************************************************************************** */
	//    ADD
	/* ****************************************************************************************************** */
	
	private function doAdd(newNode:INode):void { 
		newNode.parentNode = this.getDefaultAddTarget();
		newNode.parentNode.children.addItem(newNode);
		
		this.invalidateList();
		
		this.openNodesUntil(newNode);
		this.selectedItem = newNode;
		 
		this.editable = true;
		this.editedItemPosition = {columnIndex: 0, rowIndex: this.selectedIndex}; // TODO duplicate code
	}
	
	public function doAddFolder():void {
		LOG.fine("doAddFolder()");
		this.doAdd(new Folder("untitled"));
	}
	
	public function doAddPlaylist():void {
		LOG.fine("doAddPlaylist()");
		this.doAdd(new Playlist("untitled"));
	}
	
	private function getDefaultAddTarget():INodeContainer {
		var defaultTarget:INode;
		
		if(this.selectedItem != null) {
			var selectedNode:INode = this.selectedItem as INode;
			if(selectedNode.children != null) { // its not a leaf
				defaultTarget = selectedNode;
			} else {
				defaultTarget = selectedNode.parentNode; // its a leaf, get parent
			}
		} else {
			defaultTarget = Model.instance.library;
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