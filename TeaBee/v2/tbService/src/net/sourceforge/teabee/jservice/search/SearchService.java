package net.sourceforge.teabee.jservice.search;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import net.sourceforge.teabee.jservice.ISearchService;


import com.google.gdata.client.youtube.YouTubeService;
import com.google.gdata.data.youtube.VideoEntry;
import com.google.gdata.data.youtube.VideoFeed;

public class SearchService implements ISearchService {
	
	private static final String SEARCH_BASE_URL = "http://gdata.youtube.com/feeds/api/videos";
	private static final int FORMAT_SWF = 5;
	
	private final YouTubeService service;
	
	
	public SearchService() {
		 this.service = new YouTubeService("My Application");
	}
	
	public SearchResult doSearch(final SearchQuery query) {
		final List<SearchResultVideo> result = new LinkedList<SearchResultVideo>();
		final String searchUrl = SEARCH_BASE_URL + "?" +
			"vq="+escape(query.getSearchTerm())+"&" +
			"orderby=published&" +
			"start-index="+query.getStartIndex()+"&" +
			"max-results="+query.getMaxResults()+"&" +
			"format="+FORMAT_SWF+"&" +
			"racy=include";
		
		try {
			final URL metaFeedUrl = new URL(searchUrl);
			
			System.out.println("Getting favorite video entries...\n");
			final VideoFeed resultFeed = this.service.getFeed(metaFeedUrl, VideoFeed.class);
			final List<VideoEntry> entries = resultFeed.getEntries();
			for (VideoEntry videoEntry : entries) {
				result.add(SearchResultVideo.newByVideoEntry(videoEntry));
			}
			
			final int totalResults = resultFeed.getTotalResults();
			
			return new SearchResult(query, result, totalResults);
		} catch(Exception e) {
			throw new RuntimeException("Could not search", e);
		}
	}
	
	private static String escape(final String string) {
		return string; // TODO url-escape!
	}
	
	
	public static void main(String[] args) {
		final SearchResult result = new SearchService().doSearch(new SearchQuery("maven", 1, 10));
		System.out.println(result);
		for (SearchResultVideo rs : result.getVideos()) {
			System.out.println(rs);
		}
		System.out.println("....");
	}
}
