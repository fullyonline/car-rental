package ch.juventus.carrental.model;

import ch.juventus.carrental.service.DateValidator;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CarFilter {
    private Date startDate;
    private Date endDate;
    private String searchQuery;
    private List<CarType> type;
    private GearShift gearShift;
    private Double minPricePerDay;
    private Double maxPricePerDay;
    private List<Integer> seats;
    private Boolean airCondition;

    @JsonCreator
    public CarFilter(@JsonProperty("startDate") Date startDate, @JsonProperty("endDate")  Date endDate,
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

    public CarFilter(){}

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
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

    public List<Car> filterCars(List<Car> cars) {
        Stream<Car> carsStream = cars.stream();

        carsStream = filterRentalDates(carsStream);

        if (searchQuery != null) {
            carsStream = carsStream.filter(c -> c.getName().toLowerCase().contains(searchQuery.toLowerCase()));
        }
        if (type != null) {
            carsStream = carsStream.filter(c -> type.contains(c.getType()));
        }
        if (gearShift != null) {
            carsStream = carsStream.filter(c -> c.getGearShift() == gearShift);
        }
        if (minPricePerDay != null) {
            carsStream = carsStream.filter(c -> c.getPricePerDay() >= minPricePerDay);
        }
        if (maxPricePerDay != null) {
            carsStream = carsStream.filter(c -> c.getPricePerDay() <= maxPricePerDay);
        }
        if (seats != null) {
            carsStream = carsStream.filter(c -> seats.contains(c.getSeats()));
        }
        if (airCondition != null) {
            carsStream = carsStream.filter(c -> c.getAirCondition() == airCondition);
        }
        return carsStream.collect(Collectors.toList());
    }

    private Stream<Car> filterRentalDates(Stream<Car> carsStream) {
        // TODO: Check if the date is in the future
        if (DateValidator.validate(startDate, endDate))
        {
            carsStream = carsStream.filter(c -> {
                boolean isRented = c.getRentals().stream().anyMatch(
                        rental -> startDate.getTime() <= rental.getEndDate().getTime() &&
                                rental.getStartDate().getTime() <= endDate.getTime());
                return !isRented;
            });
        }

        return carsStream;
    }

}
