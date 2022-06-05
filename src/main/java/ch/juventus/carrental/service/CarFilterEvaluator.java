package ch.juventus.carrental.service;

import ch.juventus.carrental.model.Car;
import ch.juventus.carrental.model.FilterDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CarFilterEvaluator {

    private final FilterDto filterDto;

    public CarFilterEvaluator(FilterDto filterDto){
        this.filterDto = filterDto;
    }

    public List<Car> filterCars(List<Car> cars) {
        if (isInvalidDateFilter())
        {
            return new ArrayList<>();
        }

        Stream<Car> carsStream = cars.stream();

        carsStream = filterRentalDates(carsStream);
        carsStream = filterName(carsStream);
        carsStream = filterCarType(carsStream);
        carsStream = filterGearShift(carsStream);
        carsStream = filterMinPricePerDay(carsStream);
        carsStream = filterMaxPricePerDay(carsStream);
        carsStream = filterSeats(carsStream);
        carsStream = filterAirCondition(carsStream);

        return carsStream.collect(Collectors.toList());
    }

    private boolean isInvalidDateFilter() {
        if(filterDto.getStartDate() == null || filterDto.getEndDate() == null){
            return true;
        }
        return !DateValidator.validate(filterDto.getStartDate(), filterDto.getEndDate()) || !DateValidator.isInTheFuture(filterDto.getStartDate());
    }

    private Stream<Car> filterName(Stream<Car> carsStream) {
        if (filterDto.getSearchQuery() != null) {
            carsStream = carsStream.filter(c -> c.getName().toLowerCase().contains(filterDto.getSearchQuery().toLowerCase()));
        }
        return carsStream;
    }

    private Stream<Car> filterCarType(Stream<Car> carsStream) {
        if (filterDto.getType() != null) {
            carsStream = carsStream.filter(c -> filterDto.getType().contains(c.getType()));
        }
        return carsStream;
    }

    private Stream<Car> filterGearShift(Stream<Car> carsStream) {
        if (filterDto.getGearShift() != null) {
            carsStream = carsStream.filter(c -> c.getGearShift() == filterDto.getGearShift());
        }
        return carsStream;
    }

    private Stream<Car> filterMinPricePerDay(Stream<Car> carsStream) {
        if (filterDto.getMinPricePerDay() != null) {
            carsStream = carsStream.filter(c -> c.getPricePerDay() >= filterDto.getMinPricePerDay());
        }
        return carsStream;
    }

    private Stream<Car> filterMaxPricePerDay(Stream<Car> carsStream) {
        if (filterDto.getMaxPricePerDay() != null) {
            carsStream = carsStream.filter(c -> c.getPricePerDay() <= filterDto.getMaxPricePerDay());
        }
        return carsStream;
    }

    private Stream<Car> filterSeats(Stream<Car> carsStream) {
        if (filterDto.getSeats() != null) {
            carsStream = carsStream.filter(c -> filterDto.getSeats().contains(c.getSeats()));
        }
        return carsStream;
    }

    private Stream<Car> filterAirCondition(Stream<Car> carsStream) {
        if (filterDto.getAirCondition() != null) {
            carsStream = carsStream.filter(c -> c.getAirCondition().equals(filterDto.getAirCondition()));
        }
        return carsStream;
    }

    private Stream<Car> filterRentalDates(Stream<Car> carsStream) {
        carsStream = carsStream.filter(c -> {
            boolean isRented = c.getRentals().stream().anyMatch(
                    rental -> filterDto.getStartDate().getTime() <= rental.getEndDate().getTime() &&
                            rental.getStartDate().getTime() <= filterDto.getEndDate().getTime());
            return !isRented;
        });
        return carsStream;
    }

}
