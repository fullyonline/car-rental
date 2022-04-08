package ch.juventus.carrental.controller;

import ch.juventus.carrental.service.CarService;
import ch.juventus.carrental.service.DefaultCarService;
import org.springframework.web.bind.annotation.*;

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

//    @InitBinder
//    public void initBinder(WebDataBinder binder){
//        binder.registerCustomEditor(Class.class, new MyClassEditor());
//    }


//    @GetMapping("/api/v1/helloworld")
//   public ResponseEntity<String> helloWorld(){
//        return new ResponseEntity<String>(carService.getGreeting(), HttpStatus.OK);
//    }

//    @GetMapping("/api/v1/helloworld/{id}")
//    public ResponseEntity<String> helloWorldWithPathVariable(@PathVariable(value="id") Long id){
//        String returnVal = "Hello World" + id;
//        return new ResponseEntity<String>(returnVal, HttpStatus.OK);
//    }
//
//    @GetMapping("/api/v1/helloworld")
//    public ResponseEntity<String> helloWorldWithRequestVariable(@RequestParam(value="test", required = false) String myParam){
//        String returnVal = "Hello World";
//
//        if(myParam != null){
//            returnVal = returnVal + " " + myParam;
//        }
//
//        return new ResponseEntity<String>(returnVal, HttpStatus.OK);
//    }
//
//    @PostMapping("/api/v1/postmapping")
//    public ResponseEntity<String> foo(@RequestBody Class myClass){
//        return new ResponseEntity<String>(myClass.getField(), HttpStatus.OK);
//    }

}
