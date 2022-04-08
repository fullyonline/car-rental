package ch.juventus.carrental.service;

import ch.juventus.carrental.persistance.CarDatabase;
import org.springframework.stereotype.Service;

@Service
public class DefaultCarService implements CarService{

    // implements business logic

    private final CarDatabase carDatabase;

    public DefaultCarService(CarDatabase carDatabase) {
        this.carDatabase = carDatabase;
    }

    public String getGreeting(){
        return carDatabase.loadGreeting();
    }
}
