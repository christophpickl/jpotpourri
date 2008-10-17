package code.controller.logic {

import code.common.StringUtil;
import code.model.vo.SearchResult;
import code.model.vo.Thumbnail;

import logging.Logger;

import mx.collections.ArrayCollection;
import mx.controls.Alert;
import mx.rpc.events.FaultEvent;
import mx.rpc.events.ResultEvent;
import mx.rpc.http.HTTPService;
import mx.utils.ObjectUtil;
	

public class YoutubeSearch {
	
	private static const LOG: Logger = Logger.getLogger("code.controller.logic.YoutubeSearch");
	
	private static const YOUTUBE_SEARCH_URL:String = "http://gdata.youtube.com/feeds/api/videos";
	
	private static const MAX_RESULT_COUNT:uint = 10; // TODO pager view start-index http param; max possible is 50 anyway...
	
	
	private static const DEFAULT_FAULT_FUNCTION: Function = function(event: FaultEvent): void {
		LOG.warning(ObjectUtil.toString(event));
		Alert.show(event.fault.message, "Youtube Search Error");
	};
	
	private var _searchString:String;
	private var _fnResult:Function;
	private var _fnFault:Function;
	
		
	/**
	 * @param searchTerm
	 * @param fnResult function(result:ArrayCollection<SearchResult>)
	 * @param fnFault def. null
	 */
	public function YoutubeSearch(searchString: String, fnResult: Function, fnFault: Function = null) {
		if(searchString == null) throw new Error("searchString == null");
		if(fnResult == null) throw new Error("fnResult == null");
		this._searchString = searchString;
		this._fnResult = fnResult;
		this._fnFault = (fnFault != null) ? fnFault : DEFAULT_FAULT_FUNCTION;
	}
	
	
	public function doSearch(): void {
		LOG.info("doSearch("+this+")");
		
		const httpService: HTTPService = new HTTPService();
		httpService.addEventListener(ResultEvent.RESULT, this.onResult);
		httpService.addEventListener(FaultEvent.FAULT, this._fnFault);
		// TODO show busy cursor
		
		
		httpService.url = this.constructRequestUrl(); 
		LOG.fine("httpService.send(url="+httpService.url+")");
		httpService.send();
	}
	
	private function constructRequestUrl():String {
		var params: Array = new Array();
		
		params["vq"] = StringUtil.escape(this._searchString); // "vq" ... video query: includes titles, keywords, descriptions, authors' usernames, and categories
		params["orderby"] = "published";
		params["start-index"] = "1"; // 1-based index
		params["max-results"] = MAX_RESULT_COUNT;
		params["format"] = 5; // 5 == format swf (1, 6 ... rtsp stuff)
		// category
		// location
		params["racy"] = "include"; // include pr0n stuff :)
		
		
		var url:String =  YOUTUBE_SEARCH_URL + "?";
		var first:Boolean = true;
		for(var paramKey:String in params) {
			if(first == true) first = false;
			else url += "&";
			url += paramKey + "=" + params[paramKey];
		}
		return url;
	}
	
	
	private function onResult(event: ResultEvent):void {
		LOG.fine("onResult(event="+event+")");
		var entries: ArrayCollection = event.result.feed.entry as ArrayCollection;
		var result: ArrayCollection = new ArrayCollection();
		
		var resultCount: uint = event.result.feed.totalResults;
		LOG.finer("resultCount = " + resultCount); // TODO pager: if resultCount > page.display_size => show pager
		
		if(resultCount > 0) {
			for (var i:int=0; i < entries.length; i++) {
				var entry:Object = entries.getItemAt(i);
				
				var entryTitle:String = entry.title;
				var entrySwfSource:String = YoutubeSearch.extractSwfSource(entry);
				LOG.finest("extracted swf source: [" + entrySwfSource + "]");
				var duration:uint = entry.group.duration.seconds as uint;
				var thumbnail: Thumbnail = extractFirstThumbnail(entry);
				var searchResult: SearchResult = new SearchResult(entryTitle, entrySwfSource, duration, thumbnail);
				
				result.addItem(searchResult);
			}
		}
		
		this._fnResult(result);
	}
	
	private static function extractFirstThumbnail(entry:Object):Thumbnail {
		var thumbnails:ArrayCollection = entry.group.thumbnail as ArrayCollection;
		var tn:Object = thumbnails.getItemAt(0);
		
		var url:String = tn.url as String;
		var width:uint = tn.width as uint;
		var height:uint = tn.height as uint;
		
		return new Thumbnail(url, width, height);
	}
	
	private static function extractSwfSource(item:Object):String {
		if(item.group.content is ArrayCollection) {
			for(var i:int=0; i < item.group.content.length; i++) {
				var obj:Object = item.group.content[i];
				if(obj.type == "application/x-shockwave-flash") {
					return obj.url;
				}
			}
		} else if(item.group.content is Object) { // ObjectProxy
			var c:Object = Object(item.group.content);
			if(c.type == "application/x-shockwave-flash") {
				trace("item ["+c.url+"]");
				return c.url;
			}
			
		}
		
		// FIXME can have no "group.content" at all!!! => user player tag instead
		LOG.warning("Could not extract swf source from item: " + item);
		return null;
	}
	
	public function toString():String {
		return "YoutubeSearch[_searchString="+this._searchString+"]";
	}
	
}
}