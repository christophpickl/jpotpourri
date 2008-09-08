package net.sourceforge.teabee.jservice.search;

import java.util.ArrayList;
import java.util.List;

import com.google.gdata.data.media.mediarss.MediaContent;
import com.google.gdata.data.media.mediarss.MediaThumbnail;
import com.google.gdata.data.youtube.VideoEntry;

public class SearchResultVideo {

	private static final String TYPE_SWF = "application/x-shockwave-flash"; // video/3gpp
	
	private String title;
	private String videoId;
	private List<Thumbnail> thumbnails;
	private int duration; // in seconds
	
	public SearchResultVideo() {
		// for flex
	}
	
	private SearchResultVideo(final String title, final String videoId, final List<Thumbnail> thumbnails, final int duration) {
		this.title = title;
		this.videoId = videoId;
		this.thumbnails = thumbnails;
		this.duration = duration;
	}
	
	public static SearchResultVideo newByVideoEntry(final VideoEntry entry) {
		final String title = entry.getTitle().getPlainText();
		final String videoId = extractVideoId(entry);
		final List<Thumbnail> thumbnails = extractThumbnails(entry);
		if(entry.getMediaGroup().getDuration().longValue() > Integer.MAX_VALUE) {
			throw new RuntimeException("duration exceeds int max!");
		}
		final int duration = entry.getMediaGroup().getDuration().intValue();
		
		return new SearchResultVideo(title, videoId, thumbnails, duration);
	}
	
	private static String extractVideoId(final VideoEntry entry) {
		String result = null;
		for (MediaContent content : entry.getMediaGroup().getContents()) {
			if(content.getType().equals(TYPE_SWF) == true) {
				if(result != null) {
					throw new RuntimeException("Multiple SWF group contents!" + entry);
				}
				// e.g.: http://www.youtube.com/v/_rgQ8JtwcCQ&f=gdata_videos&c=My Application
				final String rawUrl = content.getUrl();
				String url = rawUrl.substring("http://www.youtube.com/v/".length());
				url = url.substring(0, url.indexOf("&"));
				result = url;
			}
		}
		if(result == null) {
			throw new RuntimeException("Not any SWF group content found! " + entry);
		}
		return result;
	}
	private static List<Thumbnail> extractThumbnails(final VideoEntry entry) {
		final List<MediaThumbnail> thumbnails = entry.getMediaGroup().getThumbnails();
		final List<Thumbnail> result = new ArrayList<Thumbnail>(thumbnails.size());
		
		for (final MediaThumbnail thumbnail : thumbnails) {
			result.add(Thumbnail.newByMediaThumbnail(thumbnail));
		}
		
		return result;
	}
	
	@Override
	public String toString() {
		return "SearchResultVideo[title="+this.title+";videoId="+this.videoId+";duration="+this.duration+";thumbnails.length="+this.thumbnails.size()+"]";
	}
	
	public String getTitle() {
		return this.title;
	}

	public String getVideoId() {
		return this.videoId;
	}

	public List<Thumbnail> getThumbnails() {
		return this.thumbnails;
	}

	public int getDuration() {
		return this.duration;
	}
	
	

	public void setTitle(String title) {
		this.title = title;
	}

	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}

	public void setThumbnails(List<Thumbnail> thumbnails) {
		this.thumbnails = thumbnails;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	
}
/*
<entry>
<id>http://gdata.youtube.com/feeds/api/videos/objt1mLg7Ak
</id>
<published>2008-08-21T23:12:40.000-07:00</published>
<updated>2008-08-21T23:14:54.000-07:00</updated>
<category scheme='http://gdata.youtube.com/schemas/2007/categories.cat' term='People'
    label='People &amp; Blogs' />
<category scheme='http://schemas.google.com/g/2005#kind' term='http://gdata.youtube.com/schemas/2007#video' />
<category scheme='http://gdata.youtube.com/schemas/2007/keywords.cat' term='asdf' />
<title type='text'>word</title>
<content type='text'>22.8.08</content>
<link rel='alternate' type='text/html' href='http://www.youtube.com/watch?v=objt1mLg7Ak' />
<link rel='http://gdata.youtube.com/schemas/2007#video.responses' type='application/atom+xml'
    href='http://gdata.youtube.com/feeds/api/videos/objt1mLg7Ak/responses' />
<link rel='http://gdata.youtube.com/schemas/2007#video.related' type='application/atom+xml'
    href='http://gdata.youtube.com/feeds/api/videos/objt1mLg7Ak/related' />
<link rel='http://gdata.youtube.com/schemas/2007#mobile' type='text/html'
    href='http://m.youtube.com/details?v=objt1mLg7Ak' />
<link rel='self' type='application/atom+xml' href='http://gdata.youtube.com/feeds/api/videos/objt1mLg7Ak' />
<author>
    <name>dorfischer</name>
    <uri>http://gdata.youtube.com/feeds/api/users/dorfischer
    </uri>
</author>
<media:group>
    <media:title type='plain'>word</media:title>
    <media:description type='plain'>22.8.08</media:description>
    <media:keywords>asdf</media:keywords>
    <yt:duration seconds='18' />
    <media:category label='People &amp; Blogs'
        scheme='http://gdata.youtube.com/schemas/2007/categories.cat'>People</media:category>
    <media:content url='http://www.youtube.com/v/objt1mLg7Ak&amp;f=gdata_videos'
        type='application/x-shockwave-flash' medium='video' isDefault='true' expression='full'
        duration='18' yt:format='5' />
    <media:content
        url='rtsp://rtsp2.youtube.com/CigLENy73wIaHwkJ7OBi1u24oRMYDSANFEgGUgxnZGF0YV92aWRlb3MM/0/0/0/video.3gp'
        type='video/3gpp' medium='video' expression='full' duration='18' yt:format='1' />
    <media:content
        url='rtsp://rtsp2.youtube.com/CigLENy73wIaHwkJ7OBi1u24oRMYESARFEgGUgxnZGF0YV92aWRlb3MM/0/0/0/video.3gp'
        type='video/3gpp' medium='video' expression='full' duration='18' yt:format='6' />
    <media:player url='http://www.youtube.com/watch?v=objt1mLg7Ak' />
    <media:thumbnail url='http://img.youtube.com/vi/objt1mLg7Ak/2.jpg'
        height='97' width='130' time='00:00:09' />
    <media:thumbnail url='http://img.youtube.com/vi/objt1mLg7Ak/1.jpg'
        height='97' width='130' time='00:00:04.500' />
    <media:thumbnail url='http://img.youtube.com/vi/objt1mLg7Ak/3.jpg'
        height='97' width='130' time='00:00:13.500' />
    <media:thumbnail url='http://img.youtube.com/vi/objt1mLg7Ak/0.jpg'
        height='240' width='320' time='00:00:09' />
</media:group>
<gd:comments>
    <gd:feedLink href='http://gdata.youtube.com/feeds/api/videos/objt1mLg7Ak/comments'
        countHint='0' />
</gd:comments>
</entry>
*/