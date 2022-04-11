package ch.juventus.carrental.persistance;

import ch.juventus.carrental.model.Car;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface CarDatabase {

    Long getNewId();
    void create(Car car);
    void delete(Integer id);
    void update(Car car);
    void select(Integer id);
    Map<Long, Car> selectAll();
}
