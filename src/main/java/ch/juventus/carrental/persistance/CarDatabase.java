package ch.juventus.carrental.persistance;

import ch.juventus.carrental.model.Car;

public interface CarDatabase {

    Long getNewId();
    void create(Car car);
    void delete(Integer id);
    void update(Car car);
    void select(Integer id);
    void selectAll();
}
