package net.sourceforge.jpotpourri.learnme {

import flash.events.Event;
import flash.filesystem.File;
import flash.filesystem.FileMode;
import flash.filesystem.FileStream;
import flash.net.FileFilter;

import logging.Logger;
	

public class FileOpener {
	
	private static const LOG:Logger = Logger.getLogger("net.sourceforge.jpotpourri.learnme.dao.Database");
	
	private var _fnOpen: Function;
	
	public function FileOpener() {
		
	}

	/**
	 * @param file
	 * @param fnOpen function with signature (file: File, result: String): void
	 **/
	public function openFile(file: File, fnOpen: Function): void {
		LOG.fine("openFile(file:"+file.nativePath+")");
		this._fnOpen = fnOpen;
		file.addEventListener(Event.SELECT, onFileOpened);
		file.browseForOpen("Select Question Catalog", [new FileFilter("XML-Files", "*.xml;")]);
	}
	
	private function onFileOpened(event:Event):void {
		LOG.fine("onFileOpened()");
		var f:File = File(event.target);
		var fs:FileStream = new FileStream();
		fs.open(f, FileMode.READ);
		const result: String = fs.readUTFBytes(fs.bytesAvailable);
		fs.close();
		this._fnOpen(f, result);
	}
}
}