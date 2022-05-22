package ch.juventus.carrental.service;

import ch.juventus.carrental.model.Car;
import ch.juventus.carrental.model.CarFilter;
import ch.juventus.carrental.model.CarType;
import ch.juventus.carrental.model.Rental;
import ch.juventus.carrental.persistance.CarDatabase;
import ch.juventus.carrental.persistance.FileCarDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class DefaultCarService implements CarService{

    // implements business logic

    private final CarDatabase fileCarDatabase;
    final Logger logger = LoggerFactory.getLogger(FileCarDatabase.class);

    public DefaultCarService(CarDatabase fileCarDatabase) {
        this.fileCarDatabase = fileCarDatabase;
    }


    @Override
    public Boolean createCar(Car car) {
        // check if the entity is valid
        if(isValidCar(car)){
            return fileCarDatabase.create(car);
        }
        return null;
    }

    @Override
    public List<Car> getCars() {
        return selectCars();
    }

    @Override
    public List<Car> getFilteredCars(CarFilter carFilter) {
        List<Car> cars = selectCars();
        return filterCars(cars, carFilter);
    }

    private List<Car> filterCars(List<Car> cars, CarFilter carFilter) {
        Stream<Car> carsStream = cars.stream();

        carsStream = filterRentalDates(carsStream, carFilter);

        if (carFilter.getSearchQuery() != null) {
            carsStream = carsStream.filter(c -> c.getName().contains(carFilter.getSearchQuery()));
        }
        List<CarType> filterType = carFilter.getType();
        if (filterType != null) {
            carsStream = carsStream.filter(c -> filterType.contains(c.getType()));
        }
        if (carFilter.getGearShift() != null) {
            carsStream = carsStream.filter(c -> c.getGearShift() == carFilter.getGearShift());
        }
        if (carFilter.getMinPricePerDay() != null) {
            carsStream = carsStream.filter(c -> c.getPricePerDay() >= carFilter.getMinPricePerDay());
        }
        if (carFilter.getMaxPricePerDay() != null) {
            carsStream = carsStream.filter(c -> c.getPricePerDay() <= carFilter.getMaxPricePerDay());
        }
        List<Integer> seatFilter = carFilter.getSeats();
        if (seatFilter != null) {
            carsStream = carsStream.filter(c -> seatFilter.contains(c.getSeats()));
        }
        if (carFilter.getAirCondition() != null) {
            carsStream = carsStream.filter(c -> c.getAirCondition() == carFilter.getAirCondition());
        }
        List<Car> filteredCars = carsStream.collect(Collectors.toList());

        return filteredCars;
    }

    private Stream<Car> filterRentalDates(Stream<Car> carsStream, CarFilter carFilter) {
            if (DateValidator.validate(carFilter.getStartDate(), carFilter.getEndDate()))
            {
                carsStream = carsStream.filter(c -> {
                    boolean isRented = c.getRentaly().stream().anyMatch(
                            rental -> carFilter.getStartDate().getTime() <= rental.getStartDate().getTime() &&
                                      rental.getStartDate().getTime() <= carFilter.getEndDate().getTime());
                    return !isRented;
                });
            }

        return carsStream;
    }

    private List<Car> selectCars() {
        return new ArrayList<>(fileCarDatabase.select().values());
    }

    @Override
    public Car getCar(Long id) { return fileCarDatabase.select(id); }

    @Override
    public Boolean updateCar(Long id, Car car) {
        if(isValidCar(car)){
            return fileCarDatabase.update(id, car);
        }
        return false;
    }

    @Override
    public Boolean deleteCar(Long id) { return fileCarDatabase.delete(id); }

    @Override
    public Boolean createRental(Long id, Rental rental) {
        if(isValidRental(rental)){
            Car selectedCar = null;
            try {
                selectedCar = fileCarDatabase.select(id);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (selectedCar != null) {
                Double totalPrice = calculateTotalPrice(rental, selectedCar.getPricePerDay());
                rental.setTotalPrice(totalPrice);
                return fileCarDatabase.createRental(id, rental);
            }
        }
        return false;
    }

    private Double calculateTotalPrice(Rental rental, Double pricePerDay) {
        final int wholeDay = 1000 * 60 * 60 * 24;
        Date startDate = rental.getStartDate();
        Date endDate = rental.getEndDate();
        long timeDiff = endDate.getTime() - startDate.getTime();
        long roundedDaysCount = Math.round((double) timeDiff / wholeDay);
        return (roundedDaysCount + 1) * pricePerDay;
    }

    /**
     * Helper
     */
    private Boolean isValidCar(Car car){
        if(!car.isValid()){
            logger.error("invalid car object car{ id {}, name {}, type {}, gearShift {}, seats {}, pricePerDay {}, airCondition {} }",
                    car.getId(), car.getName(), car.getType(), car.getGearShift(), car.getSeats(), car.getPricePerDay(), car.getAirCondition());

            // these values must be positive
            if(car.getSeats() < 1){
                logger.error("seats can't be under 1: {}", car.getSeats());
            }
            if(car.getPricePerDay() < 1){
                logger.error("price per day can't be under 1: {}", car.getPricePerDay());
            }
            return false;
        }
        return true;
    }

    private Boolean isValidRental(Rental rental) {
        if(!rental.isValid()){
            logger.error("invalid rental object rental{ startDate {}, endDate {}, totalPrice {} }",
                    rental.getStartDate(), rental.getEndDate(), rental.getTotalPrice());

            // these values must be non-null
            if(rental.getStartDate() == null){
                logger.error("startDate can't be null");
            }
            if(rental.getEndDate() == null){
                logger.error("endDate can't be null");
            }
            return false;
        }
        return true;
    }
}
