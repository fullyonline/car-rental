package ch.juventus.carrental.service;

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
}
