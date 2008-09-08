package net.sourceforge.teabee.air.search {

import mx.collections.ArrayCollection;
	
[Bindable]
[RemoteClass(alias="net.sourceforge.teabee.jservice.search.SearchResultVideo")]
public class SearchResultVideo {
		
		public var title: String;
		public var videoId: String;
		public var thumbnails: ArrayCollection; // List<Thumbnail>
		public var duration:int; // in seconds
	
		public function SearchResultVideo() {
			
		}

}
}
