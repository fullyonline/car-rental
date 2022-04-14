package ch.juventus.carrental.persistance;

import ch.juventus.carrental.model.Car;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.nio.file.Paths;
import java.util.*;

@Repository
public class FileCarDatabase implements CarDatabase {

    final Logger logger = LoggerFactory.getLogger(FileCarDatabase.class);
    private Long highestGivenId;

    private final String fileName = "car-rental\\src\\main\\java\\ch\\juventus\\carrental\\database\\cars.json";

    // read / write data

    public FileCarDatabase(){
        highestGivenId  = Long.valueOf(0);
    }

    private void WriteToFile(Map<Long, Car> cars){
        try{
            // create object mapper instance
            ObjectMapper mapper = new ObjectMapper();

            // convert book map to JSON file
            mapper.writeValue(Paths.get(fileName).toFile(), cars);
        }
        catch(Exception ex){
            logger.error(ex.getMessage());
        }
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
    public void create(Car car) {
        car.setId(getNewId());
        Map<Long, Car> cars = select();
        logger.info("create new car{ id {}, name {}, type {}, gearShift {}, seats {}, pricePerDay {}, airCondition {} }",
                car.getId(), car.getName(), car.getType(), car.getGearShift(), car.getSeats(), car.getPricePerDay(), car.getAirCondition());
        cars.put(car.getId(), car);
        // Write the file down
        WriteToFile(cars);
    }

    @Override
    public void delete(Integer id) {

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
            WriteToFile(cars);
            return true;
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
        throw new IllegalArgumentException("No Car with this Id");
    }

    @Override
    public Map<Long, Car> select() {
        // Logger call
        logger.info("select");
        // initialize
        Map<Long, Car> cars = new HashMap<>();
        File file = new File(fileName);
        if(file.exists() && file.isFile()){
            logger.info("file {} exists", fileName);
            try{
                // create object mapper instance
                ObjectMapper mapper = new ObjectMapper();

                // define the type we are reading
                TypeReference<HashMap<Long, Car>> typeRef = new TypeReference<>() {};

                // convert JSON array to list of cars
                cars = mapper.readValue(file, typeRef);
            }
            catch(Exception ex){
                logger.error(ex.getMessage());
            }
        }
        else {
            logger.info("file {} does not exist", fileName);
        }
        return cars;
    }
}
