package net.sourceforge.jpotpourri.util;

import java.io.File;

/**
 * 
 * @author christoph_pickl@users.sourceforge.net
 */
public class StringUtil {

//    private static final Log LOG = LogFactory.getLog(StringUtil.class);
    
    protected StringUtil() {
        // no instantiation
    }
    
    public static String asString(final File... files) {
        if(files.length == 0) {
        	return "[]";
        }
        
        final StringBuilder sb = new StringBuilder();
        sb.append("[");
        boolean first = true;
        for (File file : files) {
            if(first == true) {
            	first = false;
            } else {
            	sb.append(", ");
            }
            sb.append(file.getAbsolutePath());
        }
        sb.append("]");
        return sb.toString();
    }
    
    public static String escapeLineFeeds(final String withLineFeeds) {
        return withLineFeeds.replaceAll("\n", "\\\\n");
    }
    
    public static String enforceMaxWidth(final String text, final int maxWidth) {
    	if(text.length() <= maxWidth) {
    		return text;
        }
    	
    	return text.substring(0, maxWidth) + "...";
    }
    
//    private static final NeedlemanWunch NEEDLEMAN_WUNCH = new NeedlemanWunch();
//    /**
//     * @see StringSimilarity#needleman()
//     */
//    public static float similarity(String s1, String s2) {
//        final float similarity = NEEDLEMAN_WUNCH.getSimilarity(s1, s2);
//        LOG.debug("returning similarity of '"+similarity+"' for string s1 '"+s1+"' and s2 '"+s2+"'.");
//        return similarity;
//    }
    

    /**
     * @author aTunes team
     */
	public static String getString(final Object... strings) {
		final StringBuilder objStringBuilder = new StringBuilder();

		for (final Object element : strings) {
			objStringBuilder.append(element);
		}
		
		return objStringBuilder.toString();
	}
	


	/** 
	 * @param in a string, eg: "SOME_LONG"
	 * @return capitalized string, eg: "SomeLong"
	 */
	public static String toCamelCaseByUnderscore(final String in) {
		String out = in.substring(0, 1) + in.substring(1).toLowerCase();
		
		int index = out.indexOf("_");
		while(index >= 0) {
			final String pre = out.substring(0, index);
			
			String post = out.substring(index + 1);
			post = post.substring(0, 1).toUpperCase() + post.substring(1);
			out = pre + post;
			
			index = out.indexOf("_");
		}
		
		return out;
	}
}
