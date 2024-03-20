package org.example;

public class Motorcycle extends Vehicle {
    public String category;
@Override
    public String toString(){
    return this.brand + " " + this.model + " " + this.year + " "+ this.price + " " + this.rented + " " + this.id + this.category +"\n";
}
@Override
    public String toCSV(){
    return this.brand + ";" + this.model + ";" + this.year + ";"+ this.price + ";" + this.rented + ";" + this.type + ";"+ this.category +";"+this.id+"\n";

}
    public Motorcycle(String brand, String model, Integer year, Integer price, Integer id, String category) {
        this.brand=brand;
        this.model=model;
        this.year=year;
        this.price=price;
        this.rented=false;
        this.id = id;
        this.type = "Motorcycle";
        this.category = category;



    }
}
