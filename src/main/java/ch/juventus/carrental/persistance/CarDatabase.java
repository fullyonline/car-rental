package ch.juventus.carrental.persistance;

import ch.juventus.carrental.model.Car;

import java.util.Map;

public interface CarDatabase {

    Boolean create(Car car);
    Boolean delete(Long id);
    Boolean update(Long id, Car car);
    Car select(Long id);
    Map<Long, Car> select();
}
