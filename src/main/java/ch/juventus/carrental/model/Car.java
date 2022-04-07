package ch.juventus.carrental.model;

import java.util.List;

public class Car {
    private Long id;
    private String name;
    private Enum<CarType> type;
    private Enum<GearShift> gearShift;
    private Integer seats;
    private Double pricePerDay;
    private Boolean airCondition;
    private List<Rental> rentaly; 	// kann als array abgespeichert werden.
}
