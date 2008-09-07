package net.sourceforge.teabee.jservice.search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SearchResult {

	private final SearchQuery query;
	
	private final List<SearchResultVideo> videos;
	
	private final int totalResults;

	
	public SearchResult(final SearchQuery query, final List<SearchResultVideo> videos, final int totalResults) {
		this.query = query;
		this.videos = Collections.unmodifiableList(new ArrayList<SearchResultVideo>(videos));
		this.totalResults = totalResults;
	}

	
	@Override
	public String toString() {
		return "SearchResult[query="+this.query+";totalResults="+this.totalResults+";videos.length="+this.videos.size()+"]";
	}
	
	
	public SearchQuery getQuery() {
		return this.query;
	}

	public List<SearchResultVideo> getVideos() {
		return this.videos;
	}

	public int getTotalResults() {
		return this.totalResults;
	}
	
}
