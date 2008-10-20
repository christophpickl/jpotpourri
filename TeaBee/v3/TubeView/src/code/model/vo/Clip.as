package code.model.vo {

import code.common.TimeUtil;

import flash.filesystem.File;
	

[Bindable]
public class Clip {
	
	public var id: String;
	
	public var title: String; 
	
	public var url: String; 
	
	public var duration: int;
	 
	public var thumbnail: Thumbnail;
	
	/** @transient */
	public var downloaded: uint = 0;
	
	private var _flvFile: File;
	
	public function Clip(id: String, title:String, url:String, duration:uint, thumbnail:Thumbnail) {
		this.id = id;
		this.title = title;
		this.url = url;
		this.duration = duration;
		this.thumbnail = thumbnail;
	}
	
	
	
	public function get durationFormatted():String {
		return TimeUtil.formatSeconds(this.duration)
	}
	
	public function get flvFile(): File {
		if(this._flvFile == null) {
			var path: String = "TubeView";
			path = path + "/" + this.id + ".flv";
			this._flvFile = File.documentsDirectory.resolvePath(path);
		}
		return this._flvFile;
	}
	
	public function toString():String {
		return "Clip[id="+id+";title="+title+"]";
	}


}
}