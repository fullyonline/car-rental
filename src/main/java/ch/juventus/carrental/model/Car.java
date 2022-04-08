package ch.juventus.carrental.model;

import java.util.List;

public class Car {
    private Long id;
    private String name;
    private Enum<CarType> type;
    private Enum<GearShift> gearShift;
    private Integer seats;
    private Double pricePerDay;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Enum<CarType> getType() {
        return type;
    }

    public void setType(Enum<CarType> type) {
        this.type = type;
    }

    public Enum<GearShift> getGearShift() {
        return gearShift;
    }

    public void setGearShift(Enum<GearShift> gearShift) {
        this.gearShift = gearShift;
    }

    public Integer getSeats() {
        return seats;
    }

    public void setSeats(Integer seats) {
        this.seats = seats;
    }

    public Double getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(Double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public Boolean getAirCondition() {
        return airCondition;
    }

    public void setAirCondition(Boolean airCondition) {
        this.airCondition = airCondition;
    }

    public List<Rental> getRentaly() {
        return rentaly;
    }

    public void setRentaly(List<Rental> rentaly) {
        this.rentaly = rentaly;
    }

    private Boolean airCondition;
    private List<Rental> rentaly; 	// kann als array abgespeichert werden.
}
