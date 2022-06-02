package ch.juventus.carrental.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class DateValidator {
    public static boolean validate(Date startDate, Date endDate) {
        if (startDate != null && endDate != null)
        {
            return isValidRange(startDate, endDate);
        }
        return false;
    }

    private static boolean isValidRange(Date startDate, Date endDate) {
        return endDate.compareTo(startDate) >= 0;
    }

    public static boolean isInTheFuture(Date givenDate) {
        Date nowDate = getLocalUtcDate();
        return isValidRange(nowDate, givenDate);
    }

    public static Date getLocalUtcDate() {
        LocalDate now = LocalDate.now();
        Date nowDate = Date.from(now.atStartOfDay()
                .atZone(ZoneId.of("UTC"))
                .toInstant());
        return nowDate;
    }
}
