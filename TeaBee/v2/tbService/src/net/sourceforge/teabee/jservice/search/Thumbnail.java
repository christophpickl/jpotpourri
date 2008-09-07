package net.sourceforge.teabee.jservice.search;

import com.google.gdata.data.media.mediarss.MediaThumbnail;

public class Thumbnail {

	private final String url;
	private final int width;
	private final int height;
	
	private Thumbnail(String url, int width, int height) {
		this.url = url;
		this.width = width;
		this.height = height;
	}
	
	public static Thumbnail newByMediaThumbnail(final MediaThumbnail thumbnail) {
		return new Thumbnail(thumbnail.getUrl(), thumbnail.getWidth(), thumbnail.getHeight());
	}
	
	@Override
	public String toString() {
		return "Thumbnail[url="+this.url+";width="+this.width+";height="+this.height+"]";
	}
	
	public String getUrl() {
		return this.url;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
	
}
/*
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
*/