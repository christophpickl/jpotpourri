package net.sourceforge.teabee.air.search {

import mx.collections.ArrayCollection;
	
	
[Bindable]
[RemoteClass(alias="net.sourceforge.teabee.jservice.search.SearchResult")]
public class SearchResult {
	
	public var query: SearchQuery;
	public var videos: ArrayCollection; // List<SearchResultVideo>
	public var totalResults: int;
	
	public function SearchResult() {
	}

}
}
