package ch.juventus.carrental.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CarFilterTest {

    private SimpleDateFormat sdf;
    List<Car> cars;
    CarFilter carFilter;

    @BeforeEach
    void beforeEach(){
        sdf = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMAN);
        carFilter = new CarFilter();
        cars = new ArrayList<>();

        Rental rental = new Rental();
        Rental rental2 = new Rental();
        Rental rental3 = new Rental();
        Rental rental4 = new Rental();

        try{
            rental.setStartDate(sdf.parse("29.05.2022"));
            rental.setEndDate(sdf.parse("30.05.2022"));
            rental2.setStartDate(sdf.parse("20.05.2022"));
            rental2.setEndDate(sdf.parse("03.06.2022"));

            rental3.setStartDate(sdf.parse("29.05.2022"));
            rental3.setEndDate(sdf.parse("02.06.2022"));
            rental4.setStartDate(sdf.parse("21.05.2022"));
            rental4.setEndDate(sdf.parse("02.06.2022"));
        } catch (ParseException e) {
            // This is a unit test. this works :)
        }

        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental);
        rentals.add(rental2);

        cars.add(new Car(1L, "BMW 3", CarType.CABRIO, GearShift.MANUAL, 2, 1000d, false, rentals));

        List<Rental> rentals2 = new ArrayList<>();
        rentals2.add(rental3);
        rentals2.add(rental4);

        cars.add(new Car(2L, "Audi 123", CarType.LIMOUSINE, GearShift.AUTOMATIC, 4, 200d, true, rentals2));
    }

    @Test
    void filterCarNameUppercase() {
        carFilter.setSearchQuery("W");

        List<Car> result = carFilter.filterCars(cars);

        assertEquals(1, result.size());
    }
    @Test
    void filterCarNameLowercase() {
        carFilter.setSearchQuery("w");

        List<Car> result = carFilter.filterCars(cars);

        assertEquals(1, result.size());
    }
    @Test
    void filterWithCarTypeAndGetOneResult() {
        List<CarType> carTypes = new ArrayList<>();
        carTypes.add(CarType.CABRIO);
        carTypes.add(CarType.COUPE);
        carFilter.setType(carTypes);

        List<Car> result = carFilter.filterCars(cars);

        assertEquals(1, result.size());
    }
    @Test
    void filterWithCarTypeAndGetTwoResult() {
        List<CarType> carTypes = new ArrayList<>();
        carTypes.add(CarType.CABRIO);
        carTypes.add(CarType.LIMOUSINE);

        carFilter.setType(carTypes);

        List<Car> result = carFilter.filterCars(cars);

        assertEquals(2, result.size());
    }
    @Test
    void filterWithGearShift() {
        carFilter.setGearShift(GearShift.MANUAL);

        List<Car> result = carFilter.filterCars(cars);

        assertEquals(1, result.size());
    }
    @Test
    void filterWithMinPriceTwoResults() {
        carFilter.setMinPricePerDay(199d);

        List<Car> result = carFilter.filterCars(cars);

        assertEquals(2, result.size());
    }
    @Test
    void filterWithMinPriceOneResults() {
        carFilter.setMinPricePerDay(201d);

        List<Car> result = carFilter.filterCars(cars);

        assertEquals(1, result.size());
    }
    @Test
    void filterWithMinPriceNoneResults() {
        carFilter.setMinPricePerDay(2001d);

        List<Car> result = carFilter.filterCars(cars);

        assertEquals(0, result.size());
    }
    @Test
    void filterWithMaxPriceTwoResults() {
        carFilter.setMaxPricePerDay(2001d);

        List<Car> result = carFilter.filterCars(cars);

        assertEquals(2, result.size());
    }
    @Test
    void filterWithMaxPriceOneResults() {
        carFilter.setMaxPricePerDay(201d);

        List<Car> result = carFilter.filterCars(cars);

        assertEquals(1, result.size());
    }
    @Test
    void filterWithMaxPriceNoneResults() {
        carFilter.setMaxPricePerDay(199d);

        List<Car> result = carFilter.filterCars(cars);

        assertEquals(0, result.size());
    }
    @Test
    void filterWithSeatsTwoResult() {
        List<Integer> seats = new ArrayList<>();
        seats.add(2);
        seats.add(4);

        carFilter.setSeats(seats);
        List<Car> result = carFilter.filterCars(cars);
        assertEquals(2, result.size());
    }
    @Test
    void filterWithSeatsOneResult() {
        List<Integer> seats = new ArrayList<>();
        seats.add(2);
        seats.add(3);

        carFilter.setSeats(seats);
        List<Car> result = carFilter.filterCars(cars);
        assertEquals(1, result.size());
    }
    @Test
    void filterWithSeatsNoneResult() {
        List<Integer> seats = new ArrayList<>();
        seats.add(1);
        seats.add(3);

        carFilter.setSeats(seats);
        List<Car> result = carFilter.filterCars(cars);
        assertEquals(0, result.size());
    }
    @Test
    void filterWithAirconditionTrue() {
        carFilter.setAirCondition(true);
        List<Car> result = carFilter.filterCars(cars);
        assertEquals(1, result.size());
    }
    @Test
    void filterWithAirconditionFalse() {
        carFilter.setAirCondition(false);
        List<Car> result = carFilter.filterCars(cars);
        assertEquals(1, result.size());
    }
    @Test
    void filterWithDatesOneResultBottomEdgecase() {
        try{
            carFilter.setStartDate(sdf.parse("19.05.2022"));
            carFilter.setEndDate(sdf.parse("20.05.2022"));
        } catch (ParseException e) {
            // This is a unit test. this works :)
        }

        List<Car> result = carFilter.filterCars(cars);
        assertEquals(1, result.size());
    }
    @Test
    void filterWithDatesOneResultTopEdgecase() {
        try{
            carFilter.setStartDate(sdf.parse("03.06.2022"));
            carFilter.setEndDate(sdf.parse("03.06.2022"));
        } catch (ParseException e) {
            // This is a unit test. this works :)
        }

        List<Car> result = carFilter.filterCars(cars);
        assertEquals(1, result.size());
    }
    @Test
    void filterWithDatesNoResultBottom() {
        try{
            carFilter.setStartDate(sdf.parse("03.05.2022"));
            carFilter.setEndDate(sdf.parse("21.05.2022"));
        } catch (ParseException e) {
            // This is a unit test. this works :)
        }

        List<Car> result = carFilter.filterCars(cars);
        assertEquals(0, result.size());
    }
    @Test
    void filterWithDatesNoResultTop() {
        try{
            carFilter.setStartDate(sdf.parse("22.05.2022"));
            carFilter.setEndDate(sdf.parse("10.06.2022"));
        } catch (ParseException e) {
            // This is a unit test. this works :)
        }

        List<Car> result = carFilter.filterCars(cars);
        assertEquals(0, result.size());
    }
}