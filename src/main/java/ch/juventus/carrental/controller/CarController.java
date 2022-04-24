package ch.juventus.carrental.controller;

import ch.juventus.carrental.model.Car;
import ch.juventus.carrental.service.CarService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200")
public class CarController {

    // only handle http requests

    private final CarService defaultCarService;

    public CarController(CarService defaultCarService) {
        this.defaultCarService = defaultCarService;
    }


    // Lektuere fuer Controller:
    // https://www.baeldung.com/spring-mvc-custom-property-editor
    // https://attacomsian.com/blog/jackson-read-write-json
    // https://www.baeldung.com/spring-boot-json


    /*
    TODO: Endpunkte, welche noch gemacht werden müssen

    POST 	/api/v1/car/{id}/rental 	--> erstellt eine neue Reservation


    GET 	/api/v1/cars?filter={
        "startDate" : "",					default: null
            "endDate" : "", 					default: null
            "searchQuery" : "",					default: null
            "type" : ["", "", ""],				default: null
            "gearShift" : "",					default: null
            "minPricePerDay" : "",				default: null
            "maxPricePerDay" : "",				default: null
            "seats" : [2, 3, 4],				default: null
            "airCondition" : true | false,		default: null
    }

    Fertige Endpunkte:

    GET 	/api/v1/cars 				--> Liefert alle Autos zurück
    GET 	/api/v1/car/{id} 			--> Liefert ein konkretes Auto zurück
    PUT 	/api/v1/car/{id} 			--> Dated ein spezifisches Auto ab
    POST 	/api/v1/car 				--> erstellt ein neues Auto
    DELETE 	/api/v1/car/{id} 			--> Löscht ein spezifisches Auto

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
    public ResponseEntity<List<Car>> GetAllCars(){
        return new ResponseEntity<>(defaultCarService.getCars(), HttpStatus.OK);
    }

}
