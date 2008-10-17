package code.controller.logic {

import flash.display.Loader;
import flash.events.Event;
import flash.net.URLRequest;
import flash.net.URLVariables;

import logging.Logger;
	

public class Swf2FlvConverter {
	
	private static const LOG: Logger = Logger.getLogger("code.controller.logic.Swf2FlvConverter");
	
	private var loader: Loader;
	
	/** method(flvUrl: String): void */
	private var fnResult: Function;
	
	private var swfUrl: String;
	
	
	
	public function Swf2FlvConverter(swfUrl: String, fnResult: Function) {
		this.fnResult = fnResult;
		this.swfUrl = swfUrl;
		
		this.loader = new Loader();
		this.loader.contentLoaderInfo.addEventListener(Event.INIT, onRequestLoaded);
	}
	
	public function doConvert(): void {
		this.loader.load(new URLRequest(this.swfUrl));
	}
		
	private function onRequestLoaded(event: Event):void {
		var vars: URLVariables = new URLVariables();
		vars.decode(loader.contentLoaderInfo.url.split("?")[1]);
		var flvUrl: String = constructFlvUrl(vars.video_id, vars.t);
		LOG.fine("onRequestLoaded(videoId="+vars.video_id+"; flvUrl="+flvUrl);
		
		this.fnResult(flvUrl);
	}
	
	private static function constructFlvUrl(video_id:String, t:String):String {
		var tmp:String = "http://www.youtube.com/get_video.php?";
		tmp += "video_id="+video_id;
		tmp += "&t="+t;
		return tmp;
	}

}
}