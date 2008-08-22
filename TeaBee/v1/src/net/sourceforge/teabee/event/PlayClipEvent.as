package net.sourceforge.teabee.event {

import com.adobe.cairngorm.control.CairngormEvent;

import net.sourceforge.teabee.valueobject.Clip;

public class PlayClipEvent extends CairngormEvent {
	
	public var clip:Clip;
	
	public static const EVENT_ID:String = "playClip";
	
	public function PlayClipEvent(clip:Clip, bubbles:Boolean=false, cancelable:Boolean=false) {
		super(EVENT_ID, bubbles, cancelable);
		
		this.clip = clip;
	}
	
	public override function toString():String {
		return "PlayClipEvent[clip=" + this.clip + "]";
	}
	
}
}