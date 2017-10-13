package edu.depaul.csc472.finalproject.Model;

/**
 * Created by Fahad on 10/12/17.
 */

public class Truck {

    public String truckImage;
    public String truckLocation;
    public String truckName;
    public String truckType;


    public Truck(String truckImage, String truckLocation, String truckName, String truckType) {

        this.truckImage = truckImage;
        this.truckLocation = truckLocation;
        this.truckName = truckName;
        this.truckType = truckType;
    }

    public Truck() {

    }

    public String getTruckImage() {
        return truckImage;
    }

    public String getTruckName() {
        return truckName;
    }

    public String getTruckLocation() {
        return truckLocation;
    }

    @Override
    public String toString() {
        return "Truck{" +
                "truckImage='" + truckImage + '\'' +
                ", location='" + truckLocation + '\'' +
                ", truckName='" + truckName + '\'' +
                ", truckType='" + truckType + '\'' +
                '}';
    }

    public String getTruckType() {
        return truckType;
    }





}
