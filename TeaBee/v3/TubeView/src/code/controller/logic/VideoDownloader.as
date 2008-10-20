package code.controller.logic {

import code.model.vo.Clip;

import flash.events.Event;
import flash.events.ProgressEvent;
import flash.filesystem.File;
import flash.filesystem.FileMode;
import flash.filesystem.FileStream;
import flash.net.URLRequest;
import flash.net.URLStream;
import flash.utils.ByteArray;

import logging.Logger;
	

public class VideoDownloader {
	
	private static const LOG: Logger = Logger.getLogger("code.controller.logic.VideoDownloader");
	
	private var clip: Clip;
	
	private var stream: URLStream;
	
	
	public function VideoDownloader(clip: Clip) {
		this.clip = clip;
	}

	public function doDownload(): void {
		LOG.info("doDownload() [clip="+this.clip+"]");
		
		new Swf2FlvConverter(this.clip.url, this.onFlvUrlReceived).doConvert();
	}
	
	public function onFlvUrlReceived(flvUrl: String): void {
		LOG.finer("onFlvUrlReceived(flvUrl="+flvUrl+")");
		var req:URLRequest = new URLRequest(flvUrl);
		stream = new URLStream();
		stream.addEventListener(Event.COMPLETE, onDownloadComplete);
		stream.addEventListener(ProgressEvent.PROGRESS, onProgress);
		stream.load(req);
	}
	
	private function onProgress(event: ProgressEvent):void {
		var percent: int = event.bytesLoaded / event.bytesTotal * 100;
		// LOG.finest("onProgress(event="+event+") percent=" + percent);
		this.clip.downloaded = percent;
	}	
	
	private function onDownloadComplete(event: Event):void {
		var fileData: ByteArray = new ByteArray();
		stream.readBytes(fileData, 0, stream.bytesAvailable);
		
		var file: File = this.clip.flvFile;
		
		LOG.fine("saving file to path [" + file.nativePath + "]");
		
		var fileStream: FileStream = new FileStream();
		fileStream.open(file, FileMode.WRITE);
		fileStream.writeBytes(fileData, 0, fileData.length);
		fileStream.close();
	}
	

}
}