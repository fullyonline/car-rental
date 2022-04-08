package ch.juventus.carrental.service;

import ch.juventus.carrental.persistance.FileCarDatabase;
import org.springframework.stereotype.Service;

@Service
public class DefaultCarService implements CarService{

    // implements business logic

    private final FileCarDatabase fileCarDatabase;

    public DefaultCarService(FileCarDatabase fileCarDatabase) {
        this.fileCarDatabase = fileCarDatabase;
    }

}
