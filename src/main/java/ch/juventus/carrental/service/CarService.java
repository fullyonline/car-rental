package ch.juventus.carrental.service;

import ch.juventus.carrental.model.Car;

import java.util.List;

public interface CarService {

    Boolean createCar(Car car);

    List<Car> getCars();

    Car getCar(Long id);

    Boolean updateCar(Long id, Car car);

    Boolean deleteCar(Long id);
}
