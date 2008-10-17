package net.sourceforge.teabee.air.search {

import logging.Logger;

import mx.controls.Alert;
import mx.managers.CursorManager;
import mx.messaging.ChannelSet;
import mx.messaging.channels.AMFChannel;
import mx.rpc.events.FaultEvent;
import mx.rpc.events.ResultEvent;
import mx.rpc.remoting.Operation;
import mx.rpc.remoting.RemoteObject;

// http://blog.atrexis.com/index.cfm/2008/6/18/There-And-Back-Again-A-Flex-To-AIR-and-BlazeDS-Tale

public class SearchService extends RemoteObject {
	
	private static const LOG: Logger = Logger.getLogger("net.sourceforge.teabee.air.search.SearchService");
	
	private static const SEARCH_SERVICE_DESTINATION: String = "searchService";
	
	private var fnResult: Function;
	
	public function SearchService() {
		super(SEARCH_SERVICE_DESTINATION);
		
		
		var channels: ChannelSet = new ChannelSet();
		var channel1:AMFChannel = new AMFChannel("my-amf", "http://localhost:8400/tbService/messagebroker/amf");
		//var ch2:AMFChannel = new AMFChannel("my-secure-amf", "https://localhost:8080/myapp/messagebroker/amfsecure");
		//var ch3:AMFChannel = new AMFChannel("my-polling-amf", "https://localhost:8080/myapp/messagebroker/amfpolling");
		//ch3.pollingEnabled = true;
		//ch3.pollingInterval = 4000;
		         
		channels.addChannel(channel1);
		//channels.addChannel(ch2);
		//channels.addChannel(ch3);
		this.channelSet = channels;
	}
	
	private var recentCusorId:int;
	public function search(query: SearchQuery, fnResult: Function):void {
		var operation: Operation = Operation(this.getOperation("doSearch")); // is actually an AbstractOperation
		var args: Array = new Array();
		args.push(query);
		operation.arguments = args;
		operation.addEventListener(FaultEvent.FAULT, onFault);
		operation.addEventListener(ResultEvent.RESULT, onResult);
		this.fnResult = fnResult;
		this.recentCusorId = CursorManager.currentCursorID;
		CursorManager.setBusyCursor();
		operation.send();
	}
	
	private function onFault(event: FaultEvent):void {
		trace("Fault! " + event);
		trace(event.fault.rootCause.cause.responseBody);
		CursorManager.removeBusyCursor();
		Alert.show(event.toString(), "Search Error!");
	}
	
	private function onResult(event: ResultEvent):void {
		trace("result received.");
		var res:SearchResult = SearchResult(event.result);
		CursorManager.removeBusyCursor();
		this.fnResult(res);
	}
}

}
