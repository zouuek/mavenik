package org.example;

public abstract class Vehicle {
    public String brand;
    public String model;
    public Integer year;
    public Integer price;
    public Boolean rented;
    public Integer id;
    public String type;

    public String toCSV(){
        return this.brand + ";"
                + this.model + ";"
                + this.year + ";"
                + this.price + ";"
                + this.rented + ";"
                + this.type + ";"
                + this.id + "\n";
    }
    @Override
    public String toString(){
        return this.brand + " "
                + this.model + " "
                + this.year + " "
                + this.price + " "
                + this.rented + " "
                + this.id + "\n";
    }



}
