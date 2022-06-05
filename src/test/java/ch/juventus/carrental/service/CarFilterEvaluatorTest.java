package ch.juventus.carrental.service;

import ch.juventus.carrental.model.*;
import ch.juventus.carrental.service.CarFilterEvaluator;
import ch.juventus.carrental.service.DateValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CarFilterEvaluatorTest {
    List<Car> cars;
    FilterDto filterDto;

    Date utcToday;

    @BeforeEach
    void beforeEach(){
        filterDto = new FilterDto();
        cars = new ArrayList<>();
        utcToday = DateValidator.getLocalUtcDate();
        filterDto.setStartDate(utcToday);
        filterDto.setEndDate(utcToday);

        Rental rental = new Rental();
        Rental rental2 = new Rental();
        Rental rental3 = new Rental();
        Rental rental4 = new Rental();

        rental.setStartDate(DateValidator.addDays(utcToday, 3));
        rental.setEndDate(DateValidator.addDays(utcToday, 4));
        rental2.setStartDate(DateValidator.addDays(utcToday, 6));
        rental2.setEndDate(DateValidator.addDays(utcToday, 8));


        rental3.setStartDate(DateValidator.addDays(utcToday, 14));
        rental3.setEndDate(DateValidator.addDays(utcToday, 14));
        rental4.setStartDate(DateValidator.addDays(utcToday, 15));
        rental4.setEndDate(DateValidator.addDays(utcToday, 16));

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
        filterDto.setSearchQuery("W");

        CarFilterEvaluator carFilterEvaluator = new CarFilterEvaluator(filterDto);
        List<Car> result = carFilterEvaluator.filterCars(cars);

        assertEquals(1, result.size());
    }
    @Test
    void filterCarNameLowercase() {
        filterDto.setSearchQuery("w");

        CarFilterEvaluator carFilterEvaluator = new CarFilterEvaluator(filterDto);
        List<Car> result = carFilterEvaluator.filterCars(cars);

        assertEquals(1, result.size());
    }
    @Test
    void filterWithCarTypeAndGetOneResult() {
        List<CarType> carTypes = new ArrayList<>();
        carTypes.add(CarType.CABRIO);
        carTypes.add(CarType.COUPE);
        filterDto.setType(carTypes);

        CarFilterEvaluator carFilterEvaluator = new CarFilterEvaluator(filterDto);
        List<Car> result = carFilterEvaluator.filterCars(cars);

        assertEquals(1, result.size());
    }
    @Test
    void filterWithCarTypeAndGetTwoResult() {
        List<CarType> carTypes = new ArrayList<>();
        carTypes.add(CarType.CABRIO);
        carTypes.add(CarType.LIMOUSINE);

        filterDto.setType(carTypes);

        CarFilterEvaluator carFilterEvaluator = new CarFilterEvaluator(filterDto);
        List<Car> result = carFilterEvaluator.filterCars(cars);

        assertEquals(2, result.size());
    }
    @Test
    void filterWithGearShift() {
        filterDto.setGearShift(GearShift.MANUAL);

        CarFilterEvaluator carFilterEvaluator = new CarFilterEvaluator(filterDto);
        List<Car> result = carFilterEvaluator.filterCars(cars);

        assertEquals(1, result.size());
    }
    @Test
    void filterWithMinPriceTwoResults() {
        filterDto.setMinPricePerDay(199d);

        CarFilterEvaluator carFilterEvaluator = new CarFilterEvaluator(filterDto);
        List<Car> result = carFilterEvaluator.filterCars(cars);

        assertEquals(2, result.size());
    }
    @Test
    void filterWithMinPriceOneResults() {
        filterDto.setMinPricePerDay(201d);

        CarFilterEvaluator carFilterEvaluator = new CarFilterEvaluator(filterDto);
        List<Car> result = carFilterEvaluator.filterCars(cars);

        assertEquals(1, result.size());
    }
    @Test
    void filterWithMinPriceNoneResults() {
        filterDto.setMinPricePerDay(2001d);

        CarFilterEvaluator carFilterEvaluator = new CarFilterEvaluator(filterDto);
        List<Car> result = carFilterEvaluator.filterCars(cars);

        assertEquals(0, result.size());
    }
    @Test
    void filterWithMaxPriceTwoResults() {
        filterDto.setMaxPricePerDay(2001d);

        CarFilterEvaluator carFilterEvaluator = new CarFilterEvaluator(filterDto);
        List<Car> result = carFilterEvaluator.filterCars(cars);

        assertEquals(2, result.size());
    }
    @Test
    void filterWithMaxPriceOneResults() {
        filterDto.setMaxPricePerDay(201d);

        CarFilterEvaluator carFilterEvaluator = new CarFilterEvaluator(filterDto);
        List<Car> result = carFilterEvaluator.filterCars(cars);

        assertEquals(1, result.size());
    }
    @Test
    void filterWithMaxPriceNoneResults() {
        filterDto.setMaxPricePerDay(199d);

        CarFilterEvaluator carFilterEvaluator = new CarFilterEvaluator(filterDto);
        List<Car> result = carFilterEvaluator.filterCars(cars);

        assertEquals(0, result.size());
    }
    @Test
    void filterWithSeatsTwoResult() {
        List<Integer> seats = new ArrayList<>();
        seats.add(2);
        seats.add(4);

        filterDto.setSeats(seats);
        CarFilterEvaluator carFilterEvaluator = new CarFilterEvaluator(filterDto);
        List<Car> result = carFilterEvaluator.filterCars(cars);
        assertEquals(2, result.size());
    }
    @Test
    void filterWithSeatsOneResult() {
        List<Integer> seats = new ArrayList<>();
        seats.add(2);
        seats.add(3);

        filterDto.setSeats(seats);
        CarFilterEvaluator carFilterEvaluator = new CarFilterEvaluator(filterDto);
        List<Car> result = carFilterEvaluator.filterCars(cars);
        assertEquals(1, result.size());
    }
    @Test
    void filterWithSeatsNoneResult() {
        List<Integer> seats = new ArrayList<>();
        seats.add(1);
        seats.add(3);

        filterDto.setSeats(seats);
        CarFilterEvaluator carFilterEvaluator = new CarFilterEvaluator(filterDto);
        List<Car> result = carFilterEvaluator.filterCars(cars);
        assertEquals(0, result.size());
    }
    @Test
    void filterWithAirconditionTrue() {
        filterDto.setAirCondition(true);
        CarFilterEvaluator carFilterEvaluator = new CarFilterEvaluator(filterDto);
        List<Car> result = carFilterEvaluator.filterCars(cars);
        assertEquals(1, result.size());
    }
    @Test
    void filterWithAirconditionFalse() {
        filterDto.setAirCondition(false);
        CarFilterEvaluator carFilterEvaluator = new CarFilterEvaluator(filterDto);
        List<Car> result = carFilterEvaluator.filterCars(cars);
        assertEquals(1, result.size());
    }
    @Test
    void filterWithDatesOneResultBottomEdgecase() {
        filterDto.setStartDate(DateValidator.addDays(utcToday, 2));
        filterDto.setEndDate(DateValidator.addDays(utcToday, 3));

        CarFilterEvaluator carFilterEvaluator = new CarFilterEvaluator(filterDto);
        List<Car> result = carFilterEvaluator.filterCars(cars);
        assertEquals(1, result.size());
    }
    @Test
    void filterWithDatesOneResultTopEdgecase() {
        filterDto.setStartDate(DateValidator.addDays(utcToday, 8));
        filterDto.setEndDate(DateValidator.addDays(utcToday, 9));

        CarFilterEvaluator carFilterEvaluator = new CarFilterEvaluator(filterDto);
        List<Car> result = carFilterEvaluator.filterCars(cars);
        assertEquals(1, result.size());
    }
    @Test
    void filterWithDatesNoResultBottom() {
        filterDto.setStartDate(DateValidator.addDays(utcToday, 3));
        filterDto.setEndDate(DateValidator.addDays(utcToday, 14));

        CarFilterEvaluator carFilterEvaluator = new CarFilterEvaluator(filterDto);
        List<Car> result = carFilterEvaluator.filterCars(cars);
        assertEquals(0, result.size());
    }
    @Test
    void filterWithDatesNoResultTop() {
        filterDto.setStartDate(DateValidator.addDays(utcToday, 8));
        filterDto.setEndDate(DateValidator.addDays(utcToday, 16));

        CarFilterEvaluator carFilterEvaluator = new CarFilterEvaluator(filterDto);
        List<Car> result = carFilterEvaluator.filterCars(cars);
        assertEquals(0, result.size());
    }
    @Test
    void filterWithInvalidDates() {
        filterDto.setStartDate(null);
        filterDto.setEndDate(null);

        CarFilterEvaluator carFilterEvaluator = new CarFilterEvaluator(filterDto);
        List<Car> result = carFilterEvaluator.filterCars(cars);
        assertEquals(0, result.size());
    }
    @Test
    void filterWithInvalidStartDate() {
        filterDto.setStartDate(null);

        CarFilterEvaluator carFilterEvaluator = new CarFilterEvaluator(filterDto);
        List<Car> result = carFilterEvaluator.filterCars(cars);
        assertEquals(0, result.size());
    }
    @Test
    void filterWithInvalidEndDate() {
        filterDto.setEndDate(null);

        CarFilterEvaluator carFilterEvaluator = new CarFilterEvaluator(filterDto);
        List<Car> result = carFilterEvaluator.filterCars(cars);
        assertEquals(0, result.size());
    }
}