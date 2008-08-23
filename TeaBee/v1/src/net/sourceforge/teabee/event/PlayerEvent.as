package net.sourceforge.teabee.event {

import flash.events.Event;
	

public class PlayerEvent extends Event {
	
	public static const PLAYER_FINISHED:String = "playerFinished";
	
	public function PlayerEvent(type:String, bubbles:Boolean=false, cancelable:Boolean=false) {
		super(type, bubbles, cancelable);
		
	}

}
}