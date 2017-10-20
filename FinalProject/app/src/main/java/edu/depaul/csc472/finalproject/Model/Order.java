package edu.depaul.csc472.finalproject.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fahad on 10/20/17.
 */

public class Order {

    public String customerUserName;
    public String truckName;
    public String orderNumber;
    public ArrayList<Meal> meals= new ArrayList<Meal>();

    @Override
    public String toString() {
        return "Order{" +
                "customerUserName='" + customerUserName + '\'' +
                ", truckName='" + truckName + '\'' +
                ", orderNumber='" + orderNumber + '\'' +
                ", meals=" + meals +
                '}';
    }

    public Order(String customerUserName, String truckName, String orderNumber, ArrayList<Meal> meals) {
        this.customerUserName = customerUserName;
        this.truckName = truckName;
        this.orderNumber = orderNumber;
        this.meals = meals;
    }

    public Order(String username, String truckName, String orderNumber, List<Meal> myOrders){

    }

    public String getCustomerUserName() {
        return customerUserName;
    }

    public String getTruckName() {
        return truckName;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public ArrayList<Meal> getMeals() {
        return meals;
    }
}
