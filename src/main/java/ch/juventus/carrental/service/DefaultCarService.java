package ch.juventus.carrental.service;

import ch.juventus.carrental.model.Car;
import ch.juventus.carrental.model.Rental;
import ch.juventus.carrental.persistance.CarDatabase;
import ch.juventus.carrental.persistance.FileCarDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        return new ArrayList(fileCarDatabase.select().values());
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
        return true;
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
}
