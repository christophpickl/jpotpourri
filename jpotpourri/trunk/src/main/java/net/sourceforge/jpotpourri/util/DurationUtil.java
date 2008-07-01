package net.sourceforge.jpotpourri.util;


/**
 * @author christoph_pickl@users.sourceforge.net
 */
public class DurationUtil {
    
	private static final double SIXTY = 60.0;
	
	
	protected DurationUtil() {
        // no instantiation
    }

    public static String formatDurationShort(final Duration duration) {
        return formatDuration(duration.getMinutes(), duration.getHours());
    }

    public static String formatDurationShort(final int durationInMinutes) {
        int minutes = durationInMinutes % 60;
        int hours = (int) Math.floor(durationInMinutes / SIXTY);
        return formatDuration(minutes, hours);
    }
    
    private static String formatDuration(final int minutes, final int hours) {
        final StringBuilder sb = new StringBuilder(10);
        sb.append(hours).append(":");
        if(minutes < 10) {
        	sb.append("0");
        }
        sb.append(minutes);
        return sb.toString();
        
    }
    
}
