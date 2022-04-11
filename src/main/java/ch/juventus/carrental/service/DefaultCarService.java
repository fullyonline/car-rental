package ch.juventus.carrental.service;

import ch.juventus.carrental.model.Car;
import ch.juventus.carrental.persistance.CarDatabase;
import ch.juventus.carrental.persistance.FileCarDatabase;
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
    public void createNewCar(Car car) {
        // TODO: Fehlerhandling bei nicht gesetzten fields
        fileCarDatabase.create(car);
    }

    @Override
    public List<Car> getAllCars() {
        return new ArrayList(fileCarDatabase.selectAll().values());
    }
}
