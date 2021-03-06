package ch.juventus.carrental.model;

import ch.juventus.carrental.service.DateValidator;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

public class Rental {
    Date startDate;
    Date endDate;
    Double totalPrice; // anzahl Tage * PreisProTag

    public Rental(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Rental() {}

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

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @JsonIgnore
    public boolean isValid(){
        return DateValidator.validate(startDate, endDate);
    }

}
