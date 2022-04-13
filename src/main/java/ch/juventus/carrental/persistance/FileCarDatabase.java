package ch.juventus.carrental.persistance;

import ch.juventus.carrental.model.Car;
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
    // TODO: auf richtige Location zeigen. Im Moment ist diese auf dem Parent directory
    private final String fileName = "cars.json";

    // read / write data

    public FileCarDatabase(){
        highestGivenId  = Long.valueOf(0);
    }

    @Override
    public Long getNewId() {
        // first iteration needs to check the ids of saved cars, next iterations --> cache
        if(highestGivenId == 0){
            logger.info("need to look for valid id");
            Map<Long, Car> cars = selectAll();

            // get the higest id of the existing list
            Iterator<Car> carIterator = cars.values().iterator();
            while(carIterator.hasNext()){
                Car nextCar = carIterator.next();
                if(nextCar.getId() > highestGivenId){
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
        Map<Long, Car> cars = selectAll();
        logger.info("create new car{ id {}, name {}, type {}, gearShift {}, seats {}, pricePerDay {}, airCondition {} }",
                car.getId(), car.getName(), car.getType(), car.getGearShift(), car.getSeats(), car.getPricePerDay(), car.getAirCondition());
        cars.put(car.getId(), car);
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

    @Override
    public void delete(Integer id) {

    }

    @Override
    public void update(Car car) {

    }

    @Override
    public void select(Integer id) {

    }

    @Override
    public Map<Long, Car> selectAll() {
        // Logger call
        logger.info("select all");
        // initialize
        Map<Long, Car> cars = new HashMap<>();
        File file = new File(fileName);
        if(file.exists() && file.isFile()){
            logger.info("file {} exists", fileName);
            try{
                // create object mapper instance
                ObjectMapper mapper = new ObjectMapper();

                // convert JSON array to list of cars
                cars = mapper.readValue(file, HashMap.class);
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
