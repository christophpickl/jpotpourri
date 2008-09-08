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
/*
<entry>
<id>http://gdata.youtube.com/feeds/api/videos/biqsxIUzi2o
</id>
<published>2008-08-21T23:11:49.000-07:00</published>
<updated>2008-08-21T23:18:09.000-07:00</updated>
<category scheme='http://gdata.youtube.com/schemas/2007/categories.cat' term='Entertainment'
    label='Entertainment' />
<category scheme='http://schemas.google.com/g/2005#kind' term='http://gdata.youtube.com/schemas/2007#video' />
<category scheme='http://gdata.youtube.com/schemas/2007/keywords.cat' term='sdfafsf' />
<title type='text'>asdf</title>
<content type='text'>sdf</content>
<link rel='alternate' type='text/html' href='http://www.youtube.com/watch?v=biqsxIUzi2o' />
<link rel='http://gdata.youtube.com/schemas/2007#video.responses' type='application/atom+xml'
    href='http://gdata.youtube.com/feeds/api/videos/biqsxIUzi2o/responses' />
<link rel='http://gdata.youtube.com/schemas/2007#video.related' type='application/atom+xml'
    href='http://gdata.youtube.com/feeds/api/videos/biqsxIUzi2o/related' />
<link rel='self' type='application/atom+xml' href='http://gdata.youtube.com/feeds/api/videos/biqsxIUzi2o' />
<author>
    <name>DTkixx</name>
    <uri>http://gdata.youtube.com/feeds/api/users/dtkixx
    </uri>
</author>
<media:group>
    <media:title type='plain'>asdf</media:title>
    <media:description type='plain'>sdf</media:description>
    <media:keywords>sdfafsf</media:keywords>
    <yt:duration seconds='87' />
    <media:category label='Entertainment'
        scheme='http://gdata.youtube.com/schemas/2007/categories.cat'>Entertainment</media:category>
    <media:content url='http://www.youtube.com/v/biqsxIUzi2o&amp;f=gdata_videos'
        type='application/x-shockwave-flash' medium='video' isDefault='true' expression='full'
        duration='87' yt:format='5' />
    <media:player url='http://www.youtube.com/watch?v=biqsxIUzi2o' />
    <media:thumbnail url='http://img.youtube.com/vi/biqsxIUzi2o/2.jpg'
        height='97' width='130' time='00:00:43.500' />
    <media:thumbnail url='http://img.youtube.com/vi/biqsxIUzi2o/1.jpg'
        height='97' width='130' time='00:00:21.750' />
    <media:thumbnail url='http://img.youtube.com/vi/biqsxIUzi2o/3.jpg'
        height='97' width='130' time='00:01:05.250' />
    <media:thumbnail url='http://img.youtube.com/vi/biqsxIUzi2o/0.jpg'
        height='240' width='320' time='00:00:43.500' />
</media:group>
<gd:comments>
    <gd:feedLink href='http://gdata.youtube.com/feeds/api/videos/biqsxIUzi2o/comments'
        countHint='0' />
</gd:comments>
</entry>
*/

