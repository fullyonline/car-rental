package ch.juventus.carrental.controller;

import ch.juventus.carrental.model.Car;
import ch.juventus.carrental.model.Rental;
import ch.juventus.carrental.service.CarService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200")
public class CarController {
    private final CarService defaultCarService;

    public CarController(CarService defaultCarService) {
        this.defaultCarService = defaultCarService;
    }

    /*
    TODO: Change Date to LocalDate in Rental-Class
    TODO: Change Date to LocalDate in Filter-Class
    */

    @PostMapping("/api/v1/car")
    public ResponseEntity<Boolean> createCar(@RequestBody Car car){
        return new ResponseEntity<>(defaultCarService.createCar(car), HttpStatus.OK);
    }

    @GetMapping("/api/v1/car/{id}")
    public ResponseEntity<Car> getCar(@PathVariable(value="id") Long id){
        return new ResponseEntity<>(defaultCarService.getCar(id), HttpStatus.OK);
    }

    @DeleteMapping("/api/v1/car/{id}")
    public ResponseEntity<Boolean> deleteCar(@PathVariable(value="id") Long id){
        return new ResponseEntity<>(defaultCarService.deleteCar(id), HttpStatus.OK);
    }

    @PutMapping("/api/v1/car/{id}")
    public ResponseEntity<Boolean> updateCar(@PathVariable(value="id") Long id, @RequestBody Car car){
        return new ResponseEntity<>(defaultCarService.updateCar(id, car), HttpStatus.OK);
    }

    @GetMapping("/api/v1/cars")
    public ResponseEntity<List<Car>> getAllCars(@RequestParam(name="filter", required=false) String filter){
        return new ResponseEntity<>(defaultCarService.getCars(filter), HttpStatus.OK);
    }

    @PutMapping("/api/v1/car/{id}/rent")
    public ResponseEntity<Boolean> createRental(@PathVariable(value="id") Long id, @RequestBody Rental rental){
        Boolean isValid = defaultCarService.createRental(id, rental);
        if (isValid){
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
    }

}
