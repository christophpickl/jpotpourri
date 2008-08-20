package net.sourceforge.teabee.valueobject {

[Bindable]
public class Clip {
	
	public var title:String;
	private var _url:String;
	private var _duration:uint;
	
	public function Clip(title:String, url:String, duration:uint) {
		this.title = title;
		this._url = url;
		this._duration = duration;
	}

	public function get url():String {
		return this._url;
	}

	public function get duration():uint {
		return this._duration;
	}
}
}