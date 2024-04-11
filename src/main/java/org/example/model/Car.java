package org.example.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("CAR")
public class Car extends Vehicle {
    public Car(String brand, String model, Integer year, Integer price, String plate) {
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.price = price;
        this.rent = false;
        this.plate = plate;
        //this.vehicle_type = this.getClass().getSimpleName();


    }
    public Car(){}
}
