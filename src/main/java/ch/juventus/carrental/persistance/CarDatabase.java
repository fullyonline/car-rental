package ch.juventus.carrental.persistance;

import ch.juventus.carrental.model.Car;
import ch.juventus.carrental.model.Rental;

import java.util.Map;

public interface CarDatabase {

    /**
     *
     * @param car a new Car class to add to the database
     * @return if the insert was successful
     */
    Boolean create(Car car);

    /**
     *
     * @param id of the car to delete
     * @return if the delete was successful
     */
    Boolean delete(Long id);

    /**
     *
     * @param id of the car to update
     * @param car the updated car object
     * @return if the update was successful
     */
    Boolean update(Long id, Car car);

    /**
     *
     * @param id of the car class which is to select
     * @return the searched car
     */
    Car select(Long id);

    /**
     *
     * @return all saved cars
     */
    Map<Long, Car> select();

    /**
     *
     * @param carId the id of the car
     * @param rental the new rental of the car
     * @return if the creation of the new rental was successful
     */
    Boolean createRental(Long carId, Rental rental);
}
