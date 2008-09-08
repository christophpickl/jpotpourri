package net.sourceforge.teabee.jservice.search;

public class SearchQuery {

	private String searchTerm;
	private int startIndex;
	private int maxResults;
	
	public SearchQuery() {
		// no-arg public constructor necessary for flex conversion
	}
	
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

	public void setSearchTerm(String searchTerm) {
		this.searchTerm = searchTerm;
	}

	public int getStartIndex() {
		return this.startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public int getMaxResults() {
		return this.maxResults;
	}

	public void setMaxResults(int maxResults) {
		this.maxResults = maxResults;
	}
	
	
		
}
