package ch.juventus.carrental.persistance;

import org.springframework.stereotype.Repository;

@Repository
public class FileCarDatabase implements CarDatabase {

    // read / write data
    // soll interface geben

    public String loadGreeting(){
        return "Hello World";
    }

}
