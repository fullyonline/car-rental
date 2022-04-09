package ch.juventus.carrental.persistance;

import ch.juventus.carrental.model.Car;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class FileCarDatabase implements CarDatabase {

    final Logger logger = LoggerFactory.getLogger(FileCarDatabase.class);
    // TODO: auf richtige Location zeigen. Im Moment ist diese auf dem Parent directory
    private final String fileName = "cars.json";

    // read / write data

    @Override
    public Long getNewId() {
        return Long.valueOf(1); // TODO: fix me
    }

    @Override
    public void create(Car car) {
        List<Car> cars = selectAll();
        logger.info("create new car{ id {}, name {}, type {}, gearShift {}, seats {}, pricePerDay {}, airCondition {} }",
                car.getId(), car.getName(), car.getType(), car.getGearShift(), car.getSeats(), car.getPricePerDay(), car.getAirCondition());
        cars.add(car);
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
    public List<Car> selectAll() {
        // Logger call
        logger.info("selectAll");
        // initialize
        List<Car> cars = new ArrayList<>();
        // TODO: Check if File is availible
        File file = new File(fileName);
        if(file.exists() && !file.isDirectory()){
            logger.info("file {} exists", fileName);
            try{
                // create object mapper instance
                ObjectMapper mapper = new ObjectMapper();

                // convert JSON array to list of cars
                cars = Arrays.asList(mapper.readValue(file, Car[].class));
            }
            catch(Exception ex){
                logger.error(ex.getMessage());
            }
        }
        else {
            logger.info("file {} does not exist", fileName);
        }
        // has to be like that --> https://www.geeksforgeeks.org/how-to-solve-java-list-unsupportedoperationexception/
        return new ArrayList<>(cars);
    }
}
