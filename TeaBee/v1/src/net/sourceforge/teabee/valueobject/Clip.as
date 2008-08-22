package net.sourceforge.teabee.valueobject {

[Bindable]
public class Clip {
	
	public var title:String;
	public var url:String;
	public var duration:uint;
	
	public function Clip(title:String, url:String, duration:uint) {
		this.title = title;
		this.url = url;
		this.duration = duration;
	}

}
}