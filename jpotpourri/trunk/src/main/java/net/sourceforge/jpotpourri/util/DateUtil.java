package net.sourceforge.jpotpourri.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author christoph_pickl@users.sourceforge.net
 */
public class DateUtil {

    protected DateUtil() {
        // no instantiation
    }
    
    public static Calendar getCalendarWithoutTime(final Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        
        return c;
    }
    
    public static Calendar getCalendarWithoutMilliSeconds(final Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.MILLISECOND, 0);
        
        return c;
    }
    
    public static Date getDateWithoutTime(final Date date) {
        return getCalendarWithoutTime(date).getTime();
    }
    
    public static Date getDateWithoutMilliSeconds(final Date date) {
        return getCalendarWithoutMilliSeconds(date).getTime();
    }
    
    public static Date getDateWithoutTimeAndChangedDays(final Date date, final int changeDays) {
        Calendar c = getCalendarWithoutTime(date);
        c.add(Calendar.DAY_OF_MONTH, changeDays);
        return c.getTime();
    }
    
    /**
     * @param daysBefore non negative number
     */
    public static Date getCurrentDateWithoutTimeAndSubtractedDays(final int daysBefore) {
        assert(daysBefore >= 0);
        
        return getDateWithoutTimeAndChangedDays(new Date(), -daysBefore);
    }
    
    public static int getCurrentYear() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.YEAR);
    }
    
    public static int compareWithoutTime(final Date d1, final Date d2) {
        return getDateWithoutTime(d1).compareTo(getDateWithoutTime(d2));
    }
    
    public static int compareWithoutMilliSeconds(final Date d1, final Date d2) {
        return getDateWithoutMilliSeconds(d1).compareTo(getDateWithoutMilliSeconds(d2));
    }
    
    public static void main(final String[] args) throws ParseException {
        final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        List<Date> dates = new LinkedList<Date>();
        dates.add(format.parse("2008-01-01 12:00:01.126"));
        dates.add(format.parse("2008-01-01 12:00:01.124"));
        dates.add(format.parse(""));
        Collections.sort(dates, new Comparator<Date>() {
            public int compare(final Date d1, final Date d2) {
                return compareWithoutMilliSeconds(d1, d2);
            }
            
        });
        System.out.println(Arrays.toString(dates.toArray()));
    }
}
