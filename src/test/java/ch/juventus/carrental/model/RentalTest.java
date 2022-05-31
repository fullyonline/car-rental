package ch.juventus.carrental.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RentalTest {

    private SimpleDateFormat sdf;
    private Rental rental;

    @BeforeEach
    void beforeEach(){
        rental = new Rental();
        sdf = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMAN);
    }

    @Test
    void rentalIsValid() {
        try{
            rental.setStartDate(sdf.parse("29.05.2022"));
            rental.setEndDate(sdf.parse("30.05.2022"));

        } catch (ParseException e) {
            // This is a unit test. this works :)
        }
        assertTrue(rental.isValid());
    }

    @Test
    void rentalIsInvalid() {
        try{
            rental.setStartDate(sdf.parse("30.05.2022"));
            rental.setEndDate(sdf.parse("29.05.2022"));

        } catch (ParseException e) {
            // This is a unit test. this works :)
        }
        assertFalse(rental.isValid());
    }

    @Test
    void rentalIsMissingEndDate() {
        try{
            rental.setEndDate(sdf.parse("29.05.2022"));

        } catch (ParseException e) {
            // This is a unit test. this works :)
        }
        assertFalse(rental.isValid());
    }

    @Test
    void rentalIsMissingStartDate() {
        try{
            rental.setStartDate(sdf.parse("29.05.2022"));

        } catch (ParseException e) {
            // This is a unit test. this works :)
        }
        assertFalse(rental.isValid());
    }
}