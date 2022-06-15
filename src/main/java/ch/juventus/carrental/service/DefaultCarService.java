package ch.juventus.carrental.service;

import ch.juventus.carrental.model.Car;
import ch.juventus.carrental.model.FilterDto;
import ch.juventus.carrental.model.Rental;
import ch.juventus.carrental.persistance.CarDatabase;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DefaultCarService implements CarService{

    // implements business logic

    private final CarDatabase fileCarDatabase;
    final Logger logger = LoggerFactory.getLogger(DefaultCarService.class);

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
    public List<Car> getCars(String filter) {
        List<Car> cars = selectCars();

        logger.info("getCars with filter: {}", filter);

        if (filter == null)
        {
            cars.sort(Comparator.comparing(c -> c.getName().toLowerCase()));
            return cars;
        }

        ObjectMapper jacksonMapper = new ObjectMapper();
        FilterDto filterDto;
        try {
            filterDto = jacksonMapper.readValue(filter, FilterDto.class);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage());
            return new ArrayList<>();
        }

        CarFilterEvaluator carFilterEvaluator = new CarFilterEvaluator(filterDto);
        cars = carFilterEvaluator.filterCars(cars);
        cars.sort(Comparator.comparing(Car::getPricePerDay));
        return cars;
    }

    private List<Car> selectCars() {
        return new ArrayList<>(fileCarDatabase.select().values());
    }

    @Override
    public Car getCar(Long id) {
        return fileCarDatabase.select(id);
    }

    @Override
    public boolean updateCar(Long id, Car car) {
        if(isValidCar(car)){
            return fileCarDatabase.update(id, car);
        }
        return false;
    }

    @Override
    public boolean deleteCar(Long id) {
        return fileCarDatabase.delete(id);
    }

    @Override
    public boolean createRental(Long id, Rental rental) {
        logger.info("createRental");
        if(isValidRental(rental)){
            Car selectedCar = fileCarDatabase.select(id);
            if (selectedCar != null) {
                Double totalPrice = calculateTotalPrice(rental, selectedCar.getPricePerDay());
                rental.setTotalPrice(totalPrice);
                logger.info("createRental with  with startDate {}, endDate {}, totalPrice {}",
                        rental.getStartDate(), rental.getEndDate(), rental.getTotalPrice());
                return fileCarDatabase.createRental(id, rental);
            }
            logger.error("no car found with id {}", id);
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
    private boolean isValidCar(Car car){
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

    private boolean isValidRental(Rental rental) {
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
