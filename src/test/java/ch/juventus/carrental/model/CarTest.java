package ch.juventus.carrental.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CarTest {

    @Test
    void carIsValid() {
        Car car = new Car(1L, "BMW 3", CarType.CABRIO, GearShift.MANUAL, 2, 1000d, false, new ArrayList<>());
        assertTrue(car.isValid());
    }

    @Test
    void carIsNotValid() {
        Car car = new Car(null, null, null, null, null, null, null, null);
        assertFalse(car.isValid());
    }

    @Test
    void notValidCarNegativeSeats() {
        Car car = new Car(1L, "BMW 3", CarType.CABRIO, GearShift.MANUAL, -2, 1000d, false, new ArrayList<>());
        assertFalse(car.isValid());
    }

    @Test
    void notValidCarNegativePricePerDay() {
        Car car = new Car(1L, "BMW 3", CarType.CABRIO, GearShift.MANUAL, 2, -1d, false, new ArrayList<>());
        assertFalse(car.isValid());
    }
}