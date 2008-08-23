package net.sourceforge.teabee.valueobject {

import mx.collections.ArrayCollection;
	

[Bindable]
public class JukeboxList {
	
	/** ArrayCollection<IPlayable> */
	public var playables:ArrayCollection = new ArrayCollection();
	
	public var currentPlayable:IPlayable;
	
	public function JukeboxList(playables:ArrayCollection) {
		this.playables = playables;
	}

}
}