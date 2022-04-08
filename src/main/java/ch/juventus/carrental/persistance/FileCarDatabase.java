package ch.juventus.carrental.persistance;

import ch.juventus.carrental.model.Car;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;

import java.nio.file.Paths;
import java.util.List;

@Repository
public class FileCarDatabase implements CarDatabase {

    // read / write data

    @Override
    public Long getNewId() {
        return Long.valueOf(1); // TODO: fix me
    }

    @Override
    public void create(Car car) {
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public void update(Car car) {

    }

    @Override
    public void select(Integer id) {

    }

    @Override
    public void selectAll() {

    }
}
