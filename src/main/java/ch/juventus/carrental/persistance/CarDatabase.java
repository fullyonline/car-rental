package ch.juventus.carrental.persistance;

import ch.juventus.carrental.model.Car;

import java.util.Map;

public interface CarDatabase {

    void create(Car car);
    void delete(Integer id);
    void update(Car car);
    Car select(Long id);
    Map<Long, Car> select();
}
