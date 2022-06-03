package ch.juventus.carrental.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class DateValidatorTest {

    Date startdate;
    Date enddate;

    @BeforeEach
    void beforeEach(){
        startdate = DateValidator.getLocalUtcDate();
        enddate = DateValidator.getLocalUtcDate();
    }

    @Test
    void validateValidDates() {
        enddate = DateValidator.addDays(enddate, 1);
        assertTrue(DateValidator.validate(startdate, enddate));
    }

    @Test
    void validateInvalidStartdate() {
        startdate = null;
        assertFalse(DateValidator.validate(startdate, enddate));
    }

    @Test
    void validateInvalidEnddate() {
        enddate = null;
        assertFalse(DateValidator.validate(startdate, enddate));
    }

    @Test
    void isInTheFuture() {
        Date newDate = DateValidator.addDays(startdate, 1);
        assertTrue(DateValidator.isInTheFuture(newDate));
    }

    @Test
    void isNotInTheFuture() {
        assertTrue(DateValidator.isInTheFuture(startdate));
    }

    @Test
    void addDays() {
        enddate = DateValidator.addDays(enddate, 3);
        long diffInMillis = Math.abs(enddate.getTime() - startdate.getTime());
        long diff = TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);
        assertEquals(3, diff);
    }
}