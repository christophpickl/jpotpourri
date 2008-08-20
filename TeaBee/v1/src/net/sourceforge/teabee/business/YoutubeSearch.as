package net.sourceforge.teabee.business {
	
import logging.Logger;

import mx.collections.ArrayCollection;
import mx.controls.Alert;
import mx.rpc.events.FaultEvent;
import mx.rpc.events.ResultEvent;
import mx.rpc.http.HTTPService;
import mx.utils.ObjectUtil;

import net.sourceforge.teabee.valueobject.SearchResult;


public class YoutubeSearch {
	
	private static const LOG:Logger = Logger.getLogger("net.sourceforge.teabee.business.YoutubeSearch");
	
	private static const YOUTUBE_SEARCH_URL:String = "http://gdata.youtube.com/feeds/api/videos";
	
	private static const DEFAULT_FAULT_FUNCTION:Function = function(event:FaultEvent):void {
		LOG.warning(ObjectUtil.toString(event));
		Alert.show(event.fault.message, "Youtube Search Error");
	};
	
	private static const MAX_RESULT_COUNT:uint = 50;
	
	
	
	private var _params:Array = new Array();
	// "?vq="+escapedSearch+"&orderby=published&start-index=1&max-results=50";
	
	private var _searchTerm:String;
	private var _fnResult:Function;
	private var _fnFault:Function;
	
	
	
	/**
	 * @param searchTerm
	 * @param fnResult function(result:ArrayCollection<SearchResult>)
	 */
	public function YoutubeSearch(searchTerm:String, fnResult:Function, fnFault:Function = null) {
		LOG.finer("creating YoutubeSearch(searchTerm="+searchTerm+")");
		this._searchTerm = searchTerm;
		this._fnResult = fnResult;
		this._fnFault = (fnFault != null) ? fnFault : DEFAULT_FAULT_FUNCTION;
		
		this._params["vq"] = YoutubeSearch.escape(this._searchTerm);
		this._params["orderby"] = "published";
		this._params["start-index"] = "1";
		this._params["max-results"] = MAX_RESULT_COUNT;
	}
	
	
	private function constructRequestUrl():String {
		var url:String =  YOUTUBE_SEARCH_URL + "?";
		var first:Boolean = true;
		for(var paramKey:String in this._params) {
			if(first == true) first = false;
			else url += "&";
			url += paramKey + "=" + this._params[paramKey];
		}
		return url;
	}
	
	/**
	 * @return ArrayCollection:YoutubeSearchResult
	 */
	public function doSearch():void {
		LOG.info("doSearch("+this+")");
		
		const httpService:HTTPService = new HTTPService();
		httpService.addEventListener(ResultEvent.RESULT, this.onResult);
		httpService.addEventListener(FaultEvent.FAULT, this._fnFault);
		// TODO show busy cursor
		
		const rootUrl:String = this.constructRequestUrl();
		httpService.url = rootUrl; 
		LOG.fine("httpService.send(url="+rootUrl+")");
		httpService.send();
	}
	
	private function onResult(event:ResultEvent):void {
		LOG.fine("onResult(event="+event+")");
		var rawResult:ArrayCollection = event.result.feed.entry as ArrayCollection;
		
		var result:ArrayCollection = new ArrayCollection();
		for (var i:int=0; i < rawResult.length; i++) {
			
			var obj:Object = rawResult.getItemAt(i);
			var entryTitle:String = obj.title;
			var entrySwfSource:String = this.extractSwfSource(obj);
			var duration:uint = obj.group.duration.seconds as uint;
			var searchResult:SearchResult = new SearchResult(entryTitle, entrySwfSource, duration);
			
			result.addItem(searchResult);
		}
		
		this._fnResult(result);
	}
	
	private function extractSwfSource(item:Object):String {
		if(item.group.content is ArrayCollection) {
			for(var i:int=0; i < item.group.content.length; i++) {
				var obj:Object = item.group.content[i];
				if(obj.type == "application/x-shockwave-flash") {
					return obj.url;
				}
			}
		// FIXME
		
		// can have no "group.content" at all!!! 
		//} else if(item.group.content.type == "application/x-shockwave-flash") {
		//	return item.group.content.url;
		}
		return null;
	}
	
	private static function escape(string:String):String {
		var tmp:String = string; // FIXME implement me
		
		return tmp;
	}
	
	public function toString():String {
		return "YoutubeSearch[_searchTerm="+_searchTerm+"]";
	}

	
}
}