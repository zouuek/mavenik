package org.example;

public class Car extends Vehicle {
    public Car(String brand, String model, Integer year, Integer price, Integer id) {
        this.brand=brand;
        this.model=model;
        this.year=year;
        this.price=price;
        this.rented=false;
        this.id = id;
        this.type = "Car";


    }
}
