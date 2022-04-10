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
import java.util.Iterator;
import java.util.List;

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
            List<Car> cars = selectAll();

            // get the higest id of the existing list
            Iterator<Car> carIterator = cars.stream().iterator();
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
        logger.info("select all");
        // initialize
        List<Car> cars = new ArrayList<>();
        // TODO: Check if File is availible
        File file = new File(fileName);
        if(file.exists() && file.isFile()){
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
