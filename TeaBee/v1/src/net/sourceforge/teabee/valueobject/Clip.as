package net.sourceforge.teabee.valueobject {
	import net.sourceforge.teabee.util.TimeUtil;
	

[Bindable]
public class Clip implements IPlayable {
	
	public var title:String;
	public var url:String;
	public var duration:uint;
	public var thumbnail:Thumbnail;
	
	public function Clip(title:String, url:String, duration:uint, thumbnail:Thumbnail) {
		this.title = title;
		this.url = url;
		this.duration = duration;
		this.thumbnail = thumbnail;
	}
	
	public function get durationFormatted():String {
		return TimeUtil.formatSeconds(this.duration)
	}
	
	public function toString():String {
		return "Clip[title="+title+";url="+url+"]";
	}

}
}