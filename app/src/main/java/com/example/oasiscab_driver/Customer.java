package com.example.oasiscab_driver;

public class Customer {

    private String customerName;
    private String distanceAway;
    private String pickUpLocation;
    private String dropOffLocation;

    public Customer(String customerName, String distanceAway,
                    String pickUpLocation, String dropOffLocation){

        this.customerName = customerName;
        this.distanceAway = distanceAway;
        this.pickUpLocation = pickUpLocation;
        this.dropOffLocation = dropOffLocation;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setDistanceAway(String distanceAway) {
        this.distanceAway = distanceAway;
    }

    public void setPickUpLocation(String pickUpLocation) {
        this.pickUpLocation = pickUpLocation;
    }

    public void setDropOffLocation(String dropOffLocation) {
        this.dropOffLocation = dropOffLocation;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getDistanceAway() {
        return distanceAway;
    }

    public String getPickUpLocation() {
        return pickUpLocation;
    }

    public String getDropOffLocation() {
        return dropOffLocation;
    }
}