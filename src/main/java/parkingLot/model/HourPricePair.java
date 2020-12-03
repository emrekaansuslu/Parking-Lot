package parkingLot.model;

import lombok.Data;

import java.io.Serializable;


public class HourPricePair  implements Serializable {

    private String hour;
    private double price;

    public HourPricePair(){}
    public HourPricePair(String hour,double price) {
        this.hour = hour;
        this.price = price;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
