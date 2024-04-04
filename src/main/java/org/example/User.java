package org.example;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;


public class User {
    public String login;
    public String password;
    public Role role;
    public String rentedVehicle;

    public User(String login, String password, Role role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }
    public User(String login, String password, String role, String rentedVehicle) {
        this.login = login;
        this.password = password;
        this.role = Role.valueOf(role.toUpperCase());
        this.rentedVehicle = rentedVehicle;
    }

    public void setRentedVehicle(String rentedVehicle){
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
