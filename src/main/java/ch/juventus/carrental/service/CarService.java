package ch.juventus.carrental.service;

import ch.juventus.carrental.model.Car;

import java.util.List;

public interface CarService {

    void createNewCar(Car car);

    List<Car> getAllCars();
}
