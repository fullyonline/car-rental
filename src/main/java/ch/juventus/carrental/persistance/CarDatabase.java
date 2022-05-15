package ch.juventus.carrental.persistance;

import ch.juventus.carrental.model.Car;
import ch.juventus.carrental.model.Rental;

import java.util.Map;

public interface CarDatabase {

    Boolean create(Car car);
    Boolean delete(Long id);
    Boolean update(Long id, Car car);
    Car select(Long id);
    Map<Long, Car> select();
    Boolean createRental(Long carId, Rental rental);
}
