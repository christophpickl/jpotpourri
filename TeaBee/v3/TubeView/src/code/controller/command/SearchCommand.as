package code.controller.command {

import code.controller.event.SearchEvent;
import code.controller.logic.YoutubeSearch;
import code.model.Model;

import com.adobe.cairngorm.commands.ICommand;
import com.adobe.cairngorm.control.CairngormEvent;

import logging.Logger;

import mx.collections.ArrayCollection;

	

public class SearchCommand implements ICommand {
	
	private static const LOG: Logger = Logger.getLogger("code.command.SearchCommand");
	
	
	public function SearchCommand() {
	}

	public function execute(_event: CairngormEvent):void {
		LOG.fine("execute(event=" + _event + ")");
		const event:SearchEvent = _event as SearchEvent;
		
		new YoutubeSearch(event.searchString, this.onSearchResult).doSearch();
	}
	
	private function onSearchResult(searchResults: ArrayCollection): void {
		LOG.info("onSearchResult(searchResults.length="+searchResults+")");
		Model.instance.searchResults = searchResults;
	}
	
}
}