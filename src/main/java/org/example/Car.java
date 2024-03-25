package org.example;

public class Car extends Vehicle {
    public Car(String brand, String model, Integer year, Integer price, String plate) {
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.price = price;
        this.rented = false;
        this.plate = plate;
        this.type = this.getClass().getSimpleName();


    }
}
