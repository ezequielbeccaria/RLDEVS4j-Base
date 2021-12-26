package rldevs4j.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Ezequiel Beccaría
 */
public class DateUtils {
    
    public enum TimeUnit {
               
        SECONDS(1000 % 60),
        MINUTES((60 * 1000) % 60),
        HOURS((60 * 60 * 1000));
        
        private long divisor;
        
        TimeUnit(long divisor){
            this.divisor = divisor;
        }
        
        public long toUnit(long value){
            return value / divisor;
        } 
        
    }
    
    /**
     * Return the difference in the selected TimeUnit between two dates.
     * @param d1
     * @param d2
     * @param tu
     * @return 
     */
    public static Long difference(Date d1, Date d2, TimeUnit tu){
        long diff = d1.getTime() - d2.getTime();
        if(diff<0)
            diff = Math.abs(diff);
        return tu.toUnit(diff);
    }
    
    public static Date add(Date d, int value, TimeUnit tu){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        
        if(null != tu) switch (tu) {
            case SECONDS:
                calendar.add(Calendar.SECOND, value);
                break;
            case MINUTES:
                calendar.add(Calendar.MINUTE, value);
                break;
            case HOURS:
                calendar.add(Calendar.HOUR_OF_DAY, value);
                break;
            default:
                break;
        }
        
        return calendar.getTime();
    }
    
    /**
     * Method that create an instance of date casting
     * dateInString param (dd/MM/yyyy).
     * @param stringDate
     * @return 
     */
    public static Date initDate(String dateInString) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.parse(dateInString);        
    }
    
    /**
     * Check if two dates are the same day.
     * @param date1
     * @param date2
     * @return 
     */
    public static boolean isSameDay(Date date1, Date date2) {
        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
        return fmt.format(date1).equals(fmt.format(date2));
    }
}
