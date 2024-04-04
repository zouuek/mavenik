package org.example;

public abstract class Vehicle {
    public String brand;
    public String model;
    public Integer year;
    public Integer price;
    public Boolean rented;
    public String plate;
    public String type;

    public String toCSV(){
        return this.brand + ";"
                + this.model + ";"
                + this.year + ";"
                + this.price + ";"
                + this.rented + ";"
                + this.type + ";"
                + this.plate;
    }
    @Override
    public String toString(){
        return this.brand + " "
                + this.model + " "
                + this.year + " "
                + this.price + " "
                + this.rented + " "
                + this.plate;
    }



}
