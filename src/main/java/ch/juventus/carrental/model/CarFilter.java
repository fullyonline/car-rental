package ch.juventus.carrental.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

public class CarFilter {
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    private String searchQuery;
    private List<CarType> type;
    private GearShift gearShift;
    private Double minPricePerDay;
    private Double maxPricePerDay;
    private List<Integer> seats;
    private Boolean airCondition;

    @JsonCreator
    public CarFilter(@JsonProperty("startDate") LocalDate startDate, @JsonProperty("endDate")  LocalDate endDate,
                     @JsonProperty("searchQuery") String searchQuery, @JsonProperty("type") List<CarType> type,
                     @JsonProperty("gearShift") GearShift gearShift, @JsonProperty("minPricePerDay") Double minPricePerDay,
                     @JsonProperty("maxPricePerDay") Double maxPricePerDay, @JsonProperty("seats") List<Integer> seats,
                     @JsonProperty("airCondition") Boolean airCondition) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.searchQuery = searchQuery;
        this.type = type;
        this.gearShift = gearShift;
        this.minPricePerDay = minPricePerDay;
        this.maxPricePerDay = maxPricePerDay;
        this.seats = seats;
        this.airCondition = airCondition;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    public List<CarType> getType() {
        return type;
    }

    public void setType(List<CarType> type) {
        this.type = type;
    }

    public GearShift getGearShift() {
        return gearShift;
    }

    public void setGearShift(GearShift gearShift) {
        this.gearShift = gearShift;
    }

    public Double getMinPricePerDay() {
        return minPricePerDay;
    }

    public void setMinPricePerDay(Double minPricePerDay) {
        this.minPricePerDay = minPricePerDay;
    }

    public Double getMaxPricePerDay() {
        return maxPricePerDay;
    }

    public void setMaxPricePerDay(Double maxPricePerDay) {
        this.maxPricePerDay = maxPricePerDay;
    }

    public List<Integer> getSeats() {
        return seats;
    }

    public void setSeats(List<Integer> seats) {
        this.seats = seats;
    }

    public Boolean getAirCondition() {
        return airCondition;
    }

    public void setAirCondition(Boolean airCondition) {
        this.airCondition = airCondition;
    }
}
