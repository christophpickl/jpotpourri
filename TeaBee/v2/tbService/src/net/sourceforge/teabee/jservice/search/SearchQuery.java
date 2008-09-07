package net.sourceforge.teabee.jservice.search;

public class SearchQuery {

	private final String searchTerm;
	private final int startIndex;
	private final int maxResults;
	
	public SearchQuery(final String searchTerm, final int startIndex, final int maxResults) {
		this.searchTerm = searchTerm;
		this.startIndex = startIndex;
		this.maxResults = maxResults;
	}
	
	@Override
	public String toString() {
		return "SearchQuery[searchTerm="+this.searchTerm+";startIndex="+this.startIndex+";maxResults="+this.maxResults+"]";
	}
	
	public String getSearchTerm() {
		return this.searchTerm;
	}
	public int getStartIndex() {
		return this.startIndex;
	}
	public int getMaxResults() {
		return this.maxResults;
	}
		
}
