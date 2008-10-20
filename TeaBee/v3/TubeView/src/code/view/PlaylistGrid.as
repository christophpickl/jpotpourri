package code.view {

import code.controller.logic.AddDelegate;
import code.model.Model;
import code.model.vo.Clip;
import code.model.vo.SearchResult;

import comp.FrameSearch;

import logging.Logger;

import mx.controls.DataGrid;
import mx.controls.List;
import mx.controls.dataGridClasses.DataGridColumn;
import mx.core.DragSource;
import mx.core.UIComponent;
import mx.events.DragEvent;
import mx.events.ListEvent;
import mx.managers.DragManager;
import mx.utils.ObjectUtil;

public class PlaylistGrid extends DataGrid {
	/*
<mx:DataGridColumn headerText="" editable="false" width="60"
	itemRenderer="net.sourceforge.teabee.view.renderer.PlaylistPlayRenderer" />
<!-- TODO disable single-click-edit and change to double-click-edit instead (like in library tree) -->
<mx:DataGridColumn headerText="Title" dataField="title" editable="true" />

<!--<mx:DataGridColumn headerText="Duration" dataField="durationFormatted" editable="false" />-->
<mx:DataGridColumn headerText="Duration" labelFunction="lblDuration" editable="false" />

<mx:DataGridColumn headerText="URL" dataField="url" editable="false" />
	*/
	private static const LOG: Logger = Logger.getLogger("code.view.PlaylistGrid");
	
	public function PlaylistGrid() {
		/*
		change="onChange(event)" ... to change Model.instance.video
		keyUp="onKeyUp(event)" ... to catch Backspace/Delete key
		*/
		this.editable = true;
		
		this.dropEnabled = true;
		this.dragMoveEnabled = true;
		
		this.addEventListener(ListEvent.CHANGE, this.onChange);
		this.addEventListener(DragEvent.DRAG_DROP, this.onDragDrop);
		this.addEventListener(DragEvent.DRAG_ENTER, this.onDragEnter);
		
		this.columns = PlaylistGrid.createColumns();
	}
	
	private function onChange(event: ListEvent): void {
		Model.instance.clip = this.selectedItem as Clip;
	}
	
	private static function createColumns(): Array {
		const columns: Array = new Array();
		
		const columnTitle: DataGridColumn = new DataGridColumn("Title");
		columnTitle.dataField = "title";
		columns.push(columnTitle);
		
		const columnDownloaded: DataGridColumn = new DataGridColumn("Dwnd");
		
		/*
		columnDownloaded.labelFunction = function(data: Object, column: DataGridColumn): Object {
			const clip: Clip = data as Clip;
			var b: CheckBox = new CheckBox();
			return b;
		}
		*/
		columns.push(columnDownloaded);
		
		return columns;
	}

	/* ****************************************************************************************************** */
	//    DRAG'N'DROP
	/* ****************************************************************************************************** */
	
	/** this function validates the drop */
	private function onDragEnter(event: DragEvent): void {
		LOG.finer("onDragEnter(event="+event+")");
		
        if (isDragInitiatorSearchResult(event) == true && Model.instance.playlist != null) {
    		// accept the drop from search panel
    		DragManager.acceptDragDrop(UIComponent(event.currentTarget));
    		// TODO change cursor icon for drop-target-enabled
        }
        event.stopImmediatePropagation();
	}
	
	private static function isDragInitiatorSearchResult(event: DragEvent):Boolean {
		if (event.dragInitiator is List) {
        	const list:List = event.dragInitiator as List;
        	if(list.id == FrameSearch.SEARCH_RESULT_LIST_ID) {
        		return true;
        	}
		}
		
		return false;
	}
	
	/** this function adds new items to the list */
	private function onDragDrop(event: DragEvent): void {
		LOG.finest("onDragDrop(event="+event+")");
				
		var ds:DragSource = event.dragSource;
		if (isDragInitiatorSearchResult(event) && ds.hasFormat("items")) {
			LOG.finer("accepting drop object.");
			
			var dropData:Array = ds.dataForFormat("items") as Array;
			if(dropData.length != 1) {
				throw new Error("dropData.length != 1 but " + dropData.length + "! " + ObjectUtil.toString(dropData));
			}
			
			var searchResult: SearchResult = dropData[0] as SearchResult;
			
			AddDelegate.addSearchResultToPlaylist(searchResult.newClip());
			
		}
		event.stopImmediatePropagation();
	}

}
}