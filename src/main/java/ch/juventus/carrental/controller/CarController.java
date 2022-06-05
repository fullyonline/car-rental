package ch.juventus.carrental.controller;

import ch.juventus.carrental.model.Car;
import ch.juventus.carrental.model.Rental;
import ch.juventus.carrental.service.CarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200")
@Tag(name="Car")
public class CarController {
    private final CarService defaultCarService;

    public CarController(CarService defaultCarService) {
        this.defaultCarService = defaultCarService;
    }

    /*
    TODO: Change Date to LocalDate in Rental-Class
    TODO: Change Date to LocalDate in Filter-Class
    */

    @GetMapping("/api/v1/cars")
    @Operation(summary="Returns all saved cars. If a filter object is passed, the cars will be filtered accordingly.", responses = {
            @ApiResponse(description = "Success", responseCode = "200")
    })
    public ResponseEntity<List<Car>> getAllCars(@RequestParam(name="filter", required=false) String filter){
        return new ResponseEntity<>(defaultCarService.getCars(filter), HttpStatus.OK);
    }

    @GetMapping("/api/v1/car/{id}")
    @Operation(summary="Returns the car with the given ID.", responses = {
            @ApiResponse(description = "Success", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Car.class))),
            @ApiResponse(description = "No car with this ID exists.", responseCode = "404",
                    content = @Content)
    })
    public ResponseEntity<Car> getCar(@PathVariable(value="id") Long id){
        Car car = defaultCarService.getCar(id);
        if(car == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(car, HttpStatus.OK);
    }

    @PostMapping("/api/v1/car")
    @Operation(summary="Creates a new car.", responses = {
            @ApiResponse(description = "Success", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Boolean.class)))
    })
    public ResponseEntity<Boolean> createCar(@RequestBody Car car){
        return new ResponseEntity<>(defaultCarService.createCar(car), HttpStatus.OK);
    }

    @PostMapping("/api/v1/car/{id}/rental")
    @Operation(summary="Creates a new rental entry on the car with the given ID.", responses = {
            @ApiResponse(description = "Success", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Boolean.class))),
            @ApiResponse(description = "No car with this ID exists.", responseCode = "400",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Boolean.class)))
    })
    public ResponseEntity<Boolean> createRental(@PathVariable(value="id") Long id, @RequestBody Rental rental){
        boolean isValid = defaultCarService.createRental(id, rental);
        if (isValid){
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/api/v1/car/{id}")
    @Operation(summary="Overwrites the car with the given ID.", responses = {
            @ApiResponse(description = "Success", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Boolean.class))),
            @ApiResponse(description = "No car with this ID exists.", responseCode = "404",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Boolean.class)))
    })
    public ResponseEntity<Boolean> updateCar(@PathVariable(value="id") Long id, @RequestBody Car car){
        if(defaultCarService.updateCar(id, car)){
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/api/v1/car/{id}")
    @Operation(summary="Deletes the car with the given ID.", responses = {
            @ApiResponse(description = "Success", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Boolean.class))),
            @ApiResponse(description = "No car with this ID exists.", responseCode = "404",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Boolean.class)))
    })
    public ResponseEntity<Boolean> deleteCar(@PathVariable(value="id") Long id){
        if(defaultCarService.deleteCar(id)){
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
    }

}
