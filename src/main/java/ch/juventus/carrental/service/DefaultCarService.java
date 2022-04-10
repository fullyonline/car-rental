package ch.juventus.carrental.service;

import ch.juventus.carrental.model.Car;
import ch.juventus.carrental.persistance.CarDatabase;
import ch.juventus.carrental.persistance.FileCarDatabase;
import org.springframework.stereotype.Service;

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
}
