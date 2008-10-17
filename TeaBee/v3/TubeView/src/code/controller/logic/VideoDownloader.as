package code.controller.logic {

import flash.events.Event;
import flash.filesystem.File;
import flash.filesystem.FileMode;
import flash.filesystem.FileStream;
import flash.net.URLRequest;
import flash.net.URLStream;
import flash.utils.ByteArray;

import logging.Logger;
	

public class VideoDownloader {
	
	private static const LOG: Logger = Logger.getLogger("code.controller.logic.VideoDownloader");
	
	private var swfUrl: String;
	
	private var stream: URLStream;
	
	
	public function VideoDownloader(swfUrl: String) {
		this.swfUrl = swfUrl;
	}

	public function doDownload(): void {
		LOG.info("doDownload() swfUrl="+this.swfUrl+"]");
		
		new Swf2FlvConverter(this.swfUrl, this.onFlvUrlReceived).doConvert();
	}
	
	public function onFlvUrlReceived(flvUrl: String): void {
		LOG.finer("onFlvUrlReceived(flvUrl="+flvUrl+")");
		var req:URLRequest = new URLRequest(flvUrl);
		stream = new URLStream();
		stream.addEventListener(Event.COMPLETE, onDownloadComplete);
		stream.load(req);
	}
		
	private function onDownloadComplete(event: Event):void {
		var fileData: ByteArray = new ByteArray();
		stream.readBytes(fileData, 0, stream.bytesAvailable);
		
		var file: File = File.documentsDirectory.resolvePath("_DELME.flv");
		trace("file.path: " + file.nativePath);
		
		var fileStream: FileStream = new FileStream();
		fileStream.open(file, FileMode.WRITE);
		fileStream.writeBytes(fileData, 0, fileData.length);
		fileStream.close();
	}

}
}