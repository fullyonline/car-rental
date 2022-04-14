package ch.juventus.carrental.service;

import ch.juventus.carrental.model.Car;
import ch.juventus.carrental.persistance.CarDatabase;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DefaultCarService implements CarService{

    // implements business logic

    private final CarDatabase fileCarDatabase;

    public DefaultCarService(CarDatabase fileCarDatabase) {
        this.fileCarDatabase = fileCarDatabase;
    }


    @Override
    public Boolean createCar(Car car) {
        // TODO: Fehlerhandling bei nicht gesetzten fields
        return fileCarDatabase.create(car);
    }

    @Override
    public List<Car> getCars() {
        return new ArrayList(fileCarDatabase.select().values());
    }

    @Override
    public Car getCar(Long id) {
        return fileCarDatabase.select(id);
    }

    @Override
    public Boolean updateCar(Long id, Car car) {
        // TODO: Fehlerhandling
        return fileCarDatabase.update(id, car);
    }

    @Override
    public Boolean deleteCar(Long id) {
        return fileCarDatabase.delete(id);
    }
}
