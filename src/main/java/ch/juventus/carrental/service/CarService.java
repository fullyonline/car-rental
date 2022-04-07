package ch.juventus.carrental.service;

import ch.juventus.carrental.persistance.CarDatabase;
import org.springframework.stereotype.Service;

@Service
public class CarService {

    // implements business logic
    // soll interface geben

    private final CarDatabase carDatabase;

    public CarService(CarDatabase carDatabase) {
        this.carDatabase = carDatabase;
    }

    public String getGreeting(){
        return carDatabase.loadGreeting();
    }
}
