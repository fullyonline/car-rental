package ch.juventus.carrental.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class DateValidator {
    private DateValidator() { }

    public static boolean validate(Date startDate, Date endDate) {
        if (startDate != null && endDate != null)
        {
            return isValidRange(startDate, endDate);
        }
        return false;
    }

    private static boolean isValidRange(Date startDate, Date endDate) {
        if(startDate != null && endDate != null){
            return endDate.compareTo(startDate) >= 0;
        }
        return false;
    }

    public static boolean isInTheFuture(Date givenDate) {
        if(givenDate != null) {
            Date nowDate = getLocalUtcDate();
            return isValidRange(nowDate, givenDate);
        }
        return false;
    }

    public static Date getLocalUtcDate() {
        LocalDate now = LocalDate.now();
        return Date.from(now.atStartOfDay()
                .atZone(ZoneId.of("UTC"))
                .toInstant());
    }

    public static Date addDays(Date date, int days)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return cal.getTime();
    }
}
