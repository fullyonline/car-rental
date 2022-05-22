package ch.juventus.carrental.service;

import java.util.Date;

public class DateValidator {
    public static boolean validate(Date startDate, Date endDate) {
        if (startDate != null && endDate != null)
        {
            return endDate.compareTo(startDate) >= 0;
        }
        return false;
    }
}
