package ch.juventus.carrental.persistance;

import ch.juventus.carrental.model.Car;
import ch.juventus.carrental.model.Rental;
import ch.juventus.carrental.service.DateValidator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class FileCarDatabase implements CarDatabase {

    final Logger logger = LoggerFactory.getLogger(FileCarDatabase.class);
    private Long highestGivenId;

    private static final String FILE_NAME = "src\\main\\resources\\static\\database.json";

    // read / write data

    public FileCarDatabase(){
        highestGivenId  = 0L;
    }

    private Boolean writeToFile(Map<Long, Car> cars){
        try{
            // create object mapper instance
            ObjectMapper mapper = new ObjectMapper();

            // convert book map to JSON file
            mapper.writeValue(Paths.get(FILE_NAME).toFile(), cars);
            return true;
        }
        catch(Exception ex){
            logger.error(ex.getMessage());
        }
        return false;
    }

    private Long getNewId() {
        // first iteration needs to check the ids of saved cars, next iterations --> cache
        if(highestGivenId == 0){
            logger.info("need to look for valid id");
            Map<Long, Car> cars = select();

            // get the higest id of the existing list
            for (Car nextCar : cars.values()) {
                if (nextCar.getId() > highestGivenId) {
                    highestGivenId = nextCar.getId();
                }
            }
        }
        // add +1 to represent a new valid id
        highestGivenId++;
        logger.info("new valid id {}", highestGivenId);
        return highestGivenId;
    }

    @Override
    public Boolean create(Car car) {
        car.setId(getNewId());
        Map<Long, Car> cars = select();
        logger.info("create new car{ id {}, name {}, type {}, gearShift {}, seats {}, pricePerDay {}, airCondition {} }",
                car.getId(), car.getName(), car.getType(), car.getGearShift(), car.getSeats(), car.getPricePerDay(), car.getAirCondition());
        cars.put(car.getId(), car);
        // Write the file down
        return writeToFile(cars);
    }

    @Override
    public Boolean delete(Long id) {
        logger.info("delete car with id {}", id);
        Map<Long, Car> cars = select();
        if(cars.containsKey(id)){
            cars.remove(id);
            return writeToFile(cars);
        }
        logger.warn("no car with id {} in database", id);
        return false;
    }

    @Override
    public Boolean update(Long id, Car car) {
        logger.info("update car with id {}", id);
        logger.info("update car with key {} in car{ id {}, name {}, type {}, gearShift {}, seats {}, pricePerDay {}, airCondition {} }",
                id, car.getId(), car.getName(), car.getType(), car.getGearShift(), car.getSeats(), car.getPricePerDay(), car.getAirCondition());
        Map<Long, Car> cars = select();
        if(cars.containsKey(id)){
            // overrides existing (key, value)
            cars.put(id, car);
            return writeToFile(cars);
        }
        return false;
    }

    @Override
    public Car select(Long id) {
        logger.info("select car with id {}", id);
        Map<Long, Car> cars = select();
        if(cars.containsKey(id)){
            return cars.get(id);
        }
        return null;
    }

    @Override
    public Map<Long, Car> select() {
        // Logger call
        logger.info("select");
        // initialize
        Map<Long, Car> cars = new HashMap<>();
        Path p = Paths.get(FILE_NAME);
        if(Files.exists(p)){
            logger.info("file {} exists", FILE_NAME);
            try{
                // create object mapper instance
                ObjectMapper mapper = new ObjectMapper();

                // define the type we are reading
                TypeReference<HashMap<Long, Car>> typeRef = new TypeReference<>() {};

                // convert JSON array to list of cars
                cars = mapper.readValue(p.toFile(), typeRef);
            }
            catch(Exception ex){
                logger.error(ex.getMessage());
            }
        }
        else {
            logger.info("file {} does not exist", FILE_NAME);
        }
        return cars;
    }

    @Override
    public Boolean createRental(Long carId, Rental rental) {
        logger.info("update rental of car with id {}", carId);
        logger.info("update rental of car with key {} in rental{ startDate {}, endDate {}, totalPrice {} }",
                carId, rental.getStartDate(), rental.getEndDate(), rental.getTotalPrice());

        if (!DateValidator.validate(rental.getStartDate(), rental.getEndDate())) {
            logger.error("Invalid dates of rental!");
            return false;
        }

        Map<Long, Car> cars = select();
        if(cars.containsKey(carId)){
            // adds rental to existingRentals
            Car existingCar = cars.get(carId);
            List<Rental> existingRentals = existingCar.getRentals();
            existingRentals.add(rental);
            return writeToFile(cars);
        }
        return false;
    }
}
