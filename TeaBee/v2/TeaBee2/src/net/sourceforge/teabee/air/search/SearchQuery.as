package net.sourceforge.teabee.air.search {

[Bindable]
[RemoteClass(alias="net.sourceforge.teabee.jservice.search.SearchQuery")]
public class SearchQuery {
	public var searchTerm:String;
	public var startIndex:int;
	public var maxResults:int;
	
	public function SearchQuery() {
		
	}

}
}
