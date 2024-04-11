package org.example.model;

import jakarta.persistence.*;
import org.example.utility.BooleanToShortConverter;

@Entity
@Table(name = "tvehicle")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "vehicle_type", discriminatorType = DiscriminatorType.STRING)
public abstract class Vehicle {
    public String brand;
    public String model;
    public Integer year;
    public Integer price;
    @Convert(converter = BooleanToShortConverter.class)
    public Boolean rent;
    @Id
    public String plate;
    //public String vehicle_type;
    @OneToOne(mappedBy = "rentedVehicle", fetch = FetchType.EAGER)
    public User user;

    public String toCSV(){
        return this.brand + ";"
                + this.model + ";"
                + this.year + ";"
                + this.price + ";"
                + this.rent + ";"
                + Object.class.getSimpleName() + ";"
                + this.plate;
    }
    @Override
    public String toString(){
        return this.brand + " "
                + this.model + " "
                + this.year + " "
                + this.price + " "
                + this.rent + " "
                + this.plate;
    }



}
