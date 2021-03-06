package ch.juventus.carrental.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Car {
    private Long id;
    private String name;
    private Enum<CarType> type;
    private Enum<GearShift> gearShift;
    private Integer seats;
    private Double pricePerDay;
    private Boolean airCondition;
    private List<Rental> rentals;

    @JsonCreator
    public Car(@JsonProperty("name") String name,@JsonProperty("type") CarType type, @JsonProperty("gearShift") GearShift gearShift,
               @JsonProperty("seats") Integer seats,@JsonProperty("pricePerDay") Double pricePerDay,@JsonProperty("airCondition") Boolean airCondition) {

        this.name = name;
        this.type = type;
        this.gearShift = gearShift;
        this.seats = seats;
        this.pricePerDay = pricePerDay;
        this.airCondition = airCondition;
        this.rentals = new ArrayList<>();
    }

    public Car(Long id, String name, Enum<CarType> type, Enum<GearShift> gearShift, Integer seats, Double pricePerDay, Boolean airCondition, List<Rental> rentals) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.gearShift = gearShift;
        this.seats = seats;
        this.pricePerDay = pricePerDay;
        this.airCondition = airCondition;
        this.rentals = rentals;
    }

    public Car(){}

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

    public List<Rental> getRentals() {
        return rentals;
    }

    public void setRentals(List<Rental> rentals) {
        this.rentals = rentals;
    }

    @JsonIgnore
    public boolean isValid(){
        return gearShift != null && airCondition != null && seats != null && type != null &&
                pricePerDay != null && pricePerDay > 0 && seats > 0;
    }

}
