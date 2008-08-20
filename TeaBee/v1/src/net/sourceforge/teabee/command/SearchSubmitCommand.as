package net.sourceforge.teabee.command {

import com.adobe.cairngorm.commands.ICommand;
import com.adobe.cairngorm.control.CairngormEvent;

import logging.Logger;

import mx.collections.ArrayCollection;

import net.sourceforge.teabee.business.YoutubeSearch;
import net.sourceforge.teabee.event.SearchSubmitEvent;
import net.sourceforge.teabee.model.Model;

public class SearchSubmitCommand implements ICommand {
	
	private static const LOG:Logger = Logger.getLogger("net.sourceforge.teabee.command.SearchSubmitCommand");
	
	public function SearchSubmitCommand() {
		
	}

	public function execute(_event:CairngormEvent):void {
		const event:SearchSubmitEvent = _event as SearchSubmitEvent;
		LOG.fine("execute(event=" + event + ")");
		Model.instance.isSearching = true;
		new YoutubeSearch(event.searchTerm, onSearchResult).doSearch();
	}
	
	private function onSearchResult(result:ArrayCollection):void {
		LOG.fine("onSearchResult(result.length=" + result.length + ")");
		Model.instance.searchResult = result;
		Model.instance.isSearching = false;
	}
	
}
}