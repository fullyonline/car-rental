package ch.juventus.carrental.service;

import ch.juventus.carrental.model.Car;
import ch.juventus.carrental.model.Rental;

import java.util.List;

public interface CarService {

    /**
     *
     * @param car a new Car class to add to the database
     * @return if the insert was successful
     */
    Boolean createCar(Car car);

    /**
     *
     * @param filter the filter string of the url. could be null.
     * @return all cars filtered, if filter is not null. otherwise all cars
     */
    List<Car> getCars(String filter);

    /**
     *
     * @param id of the car class which is to select
     * @return the searched car
     */
    Car getCar(Long id);

    /**
     *
     * @param id of the car to update
     * @param car the updated car object
     * @return if the update was successful
     */
    Boolean updateCar(Long id, Car car);

    /**
     *
     * @param id of the car to delete
     * @return if the delete was successful
     */
    Boolean deleteCar(Long id);

    /**
     *
     * @param id the id of the car
     * @param rental the new rental of the car
     * @return if the creation of the new rental was successful
     */
    Boolean createRental(Long id, Rental rental);
}
