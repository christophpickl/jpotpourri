package net.sourceforge.jpotpourri.util;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
public final class Duration {
    
	private final int minutes;
    private final int hours;
    private final int totalInMinutes;
    
    private Duration(final int minutes, final int hours) {
        this.minutes = minutes;
        this.hours = hours;
        this.totalInMinutes = calcTotalMinutes(minutes, hours);
    }
    
    public int getHours() {
        return this.hours;
    }
    
    public int getMinutes() {
        return this.minutes;
    }
    
    public int getTotalInMinutes() {
        return this.totalInMinutes;
    }
    
    public String formatStringShort() {
        return DurationUtil.formatDurationShort(this);
    }
    
    private static int calcTotalMinutes(final int minutes, final int hours) {
        return hours * 60 + minutes;
    }
    
    public static Duration newByTotal(final int totalMinutes) {
        final int minutes = totalMinutes % 60;
        // TODO findbugs said: int value cast to double and then passed to Math.ceil
        final int hours = (int) Math.ceil(totalMinutes / 60);
        return new Duration(minutes, hours);
    }
    
    public static Duration newByMinHour(final int minutes, final int hours) {
        return new Duration(minutes, hours);
    }
}
