package ch.juventus.carrental.service;

import ch.juventus.carrental.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

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
        filterCarName("W");
    }

    @Test
    void filterCarNameLowercase() {
        filterCarName("w");
    }

    private void filterCarName(String searchQuery) {
        filterDto.setSearchQuery(searchQuery);

        CarFilterEvaluator carFilterEvaluator = new CarFilterEvaluator(filterDto);
        List<Car> result = carFilterEvaluator.filterCars(cars);

        assertEquals(1, result.size());
    }

    @Test
    void filterWithCarTypeAndGetOneResult() {
        List<CarType> carTypes = new ArrayList<>();
        carTypes.add(CarType.CABRIO);
        carTypes.add(CarType.COUPE);

        filterWithCarType(carTypes, 1);
    }

    @Test
    void filterWithCarTypeAndGetTwoResult() {
        List<CarType> carTypes = new ArrayList<>();
        carTypes.add(CarType.CABRIO);
        carTypes.add(CarType.LIMOUSINE);

        filterWithCarType(carTypes, 2);
    }

    private void filterWithCarType(List<CarType> carTypes, int resultSize) {
        filterDto.setType(carTypes);

        CarFilterEvaluator carFilterEvaluator = new CarFilterEvaluator(filterDto);
        List<Car> result = carFilterEvaluator.filterCars(cars);

        assertEquals(resultSize, result.size());
    }

    @Test
    void filterWithGearShift() {
        filterDto.setGearShift(GearShift.MANUAL);

        CarFilterEvaluator carFilterEvaluator = new CarFilterEvaluator(filterDto);
        List<Car> result = carFilterEvaluator.filterCars(cars);

        assertEquals(1, result.size());
    }

    @ParameterizedTest
    @CsvSource({"199,2", "201,1", "2001,0"})
    void filterWithMinPrice(double minPricePerDay, int resultSize) {
        filterDto.setMinPricePerDay(minPricePerDay);

        CarFilterEvaluator carFilterEvaluator = new CarFilterEvaluator(filterDto);
        List<Car> result = carFilterEvaluator.filterCars(cars);

        assertEquals(resultSize, result.size());
    }

    @ParameterizedTest
    @CsvSource({"2001,2", "201,1", "199,0"})
    void filterWithMaxPrice(double maxPricePerDay, int resultSize) {
        filterDto.setMaxPricePerDay(maxPricePerDay);

        CarFilterEvaluator carFilterEvaluator = new CarFilterEvaluator(filterDto);
        List<Car> result = carFilterEvaluator.filterCars(cars);

        assertEquals(resultSize, result.size());
    }

    @ParameterizedTest
    @CsvSource({"2,4,2", "2,3,1", "1,3,0"})
    void filterWithSeats(int seatCount1, int seatCount2, int resultSize) {
        List<Integer> seats = new ArrayList<>();
        seats.add(seatCount1);
        seats.add(seatCount2);

        filterDto.setSeats(seats);
        CarFilterEvaluator carFilterEvaluator = new CarFilterEvaluator(filterDto);
        List<Car> result = carFilterEvaluator.filterCars(cars);
        assertEquals(resultSize, result.size());
    }

    @ParameterizedTest
    @CsvSource({"true,1", "false,1"})
    void filterWithAircondition(boolean airCondition, int resultSize) {
        filterDto.setAirCondition(airCondition);
        CarFilterEvaluator carFilterEvaluator = new CarFilterEvaluator(filterDto);
        List<Car> result = carFilterEvaluator.filterCars(cars);
        assertEquals(resultSize, result.size());
    }

    @Test
    void filterWithDatesOneResultBottomEdgecase() {
        Date filterStartDate = DateValidator.addDays(this.utcToday, 2);
        Date filterEndDate = DateValidator.addDays(this.utcToday, 3);
        int resultSize = 1;
        filterWithDates(filterStartDate, filterEndDate, resultSize);
    }

    @Test
    void filterWithDatesOneResultTopEdgecase() {
        Date filterStartDate = DateValidator.addDays(utcToday, 8);
        Date filterEndDate = DateValidator.addDays(utcToday, 9);
        int resultSize = 1;
        filterWithDates(filterStartDate, filterEndDate, resultSize);
    }

    @Test
    void filterWithDatesNoResultBottom() {
        Date filterStartDate = DateValidator.addDays(utcToday, 3);
        Date filterEndDate = DateValidator.addDays(utcToday, 14);
        int resultSize = 0;
        filterWithDates(filterStartDate, filterEndDate, resultSize);
    }

    @Test
    void filterWithDatesNoResultTop() {
        Date filterStartDate = DateValidator.addDays(utcToday, 8);
        Date filterEndDate = DateValidator.addDays(utcToday, 16);
        int resultSize = 0;
        filterWithDates(filterStartDate, filterEndDate, resultSize);
    }

    @Test
    void filterWithInvalidDates() {
        Date filterStartDate = null;
        Date filterEndDate = null;
        int resultSize = 0;
        filterWithDates(filterStartDate, filterEndDate, resultSize);
    }

    private void filterWithDates(Date filterStartDate, Date filterEndDate, int resultSize) {
        filterDto.setStartDate(filterStartDate);
        filterDto.setEndDate(filterEndDate);

        CarFilterEvaluator carFilterEvaluator = new CarFilterEvaluator(filterDto);
        List<Car> result = carFilterEvaluator.filterCars(cars);
        assertEquals(resultSize, result.size());
    }
    
    @Test
    void filterWithInvalidStartDate() {
        filterDto.setStartDate(null);
        filterWithInvalidDate();
    }

    @Test
    void filterWithInvalidEndDate() {
        filterDto.setEndDate(null);
        filterWithInvalidDate();
    }

    private void filterWithInvalidDate() {
        CarFilterEvaluator carFilterEvaluator = new CarFilterEvaluator(filterDto);
        List<Car> result = carFilterEvaluator.filterCars(cars);
        assertEquals(0, result.size());
    }
}