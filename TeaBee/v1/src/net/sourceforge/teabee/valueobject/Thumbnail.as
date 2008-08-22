package net.sourceforge.teabee.valueobject {

[Bindable]
public class Thumbnail {
	
	public var url:String;
	
	public var width:uint;
	public var height:uint;
	
	public function Thumbnail(url:String, width:uint, height:uint) {
		this.url = url;
		this.width = width;
		this.height = height;
	}

	public function toString():String {
		return "Thumbnail[url="+url+"]";
	}
}
}