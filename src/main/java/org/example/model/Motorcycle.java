package org.example.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("MOTORCYCLE")
public class Motorcycle extends Vehicle {
    public String category;
@Override
    public String toString(){
    return this.brand + " " +
            this.model + " " +
            this.year + " " +
            this.price + " " +
            this.rent + " " +
            this.plate + " " +
            this.category + "\n";
}
@Override
    public String toCSV(){
    return this.brand + ";" +
            this.model + ";" +
            this.year + ";"+
            this.price + ";" +
            this.rent + ";" +
            Object.class.getSimpleName() + ";" +
            this.category + ";" +
            this.plate+ "\n";
}
    public Motorcycle(String brand, String model, Integer year, Integer price, String plate, String category) {
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.price = price;
        this.rent = false;
        this.plate = plate;
        //this.vehicle_type = this.getClass().getSimpleName();
        this.category = category;
    }
    public Motorcycle(){}
}
