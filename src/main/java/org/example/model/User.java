package org.example.model;

import jakarta.persistence.*;
import org.example.dao.csv.CsvUserRepository;
import org.example.dao.csv.CsvVehicleRepository;

import java.io.IOException;

@Entity
@Table(name = "tuser")
public class User {
    @Id
    public String login;
    public String password;
    @Enumerated(EnumType.STRING)
    public Role role;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rentedplate", referencedColumnName = "plate")
    public Vehicle rentedVehicle;
    //public String rentedVehicle;

    public User(String login, String password, Role role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }
    public User(String login, String password, String role, String rentedVehicle) {
        this.login = login;
        this.password = password;
        this.role = Role.valueOf(role.toUpperCase());
        this.rentedVehicle = new Car("def","def",0,0,rentedVehicle); // xd xd dx
        //rentedVehicle.plate = rentedVehicle;
    }
    public User(){}

    public void setRentedVehicle(Vehicle rentedVehicle){
        this.rentedVehicle = rentedVehicle;
    }

    public String toCSV(){
        return this.login + ";" + this.password + ";" + this.role + ";" + this.rentedVehicle;
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", rentedVehicle=" + rentedVehicle +
                '}';
    }

    public void addVehicle(CsvVehicleRepository csvVehicleRepository, Vehicle vehicle) throws IOException {
        if(this.role.name().equals("ADMIN")){
            csvVehicleRepository.addVehicle(vehicle);
        }
        else System.out.println("Not enough permissions");
    }
    public void delVehicle(CsvVehicleRepository csvVehicleRepository, String plate) throws IOException {
        if(this.role.name().equals("ADMIN")) {
            for (Vehicle v : csvVehicleRepository.vehicles) {
                if (v.plate.equals(plate)) {
                    csvVehicleRepository.removeVehicle(plate);
                }
            }
        }
        else System.out.println("Not enough permissions");
    }

    public void adminBrowseUsers(CsvUserRepository csvUserRepository){
        if(this.role.name().equals("ADMIN")) {
            for (User usr : csvUserRepository.users) {
                System.out.println(usr.toString());
            }
        }
    }
    public void userCheckSelf(){
        System.out.println(this.toString());
    }

    public enum Role{
        USER,ADMIN;
    }

}
