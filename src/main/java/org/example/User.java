package org.example;

import org.apache.commons.codec.digest.DigestUtils;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.IOException;

public class User {
    public String login;
    public String  password;
    public String role;
    public Vehicle rentedVehicle;

    public User(String login, String password, String role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public void rentVehicle(Vehicle veh){
        veh.rented = true;
        this.rentedVehicle = veh;
    }
    public void deRentVehicle(Vehicle veh){
        veh.rented = false;
        this.rentedVehicle = null;
    }
    public String toCSV(){
        String encryptedPassword = DigestUtils.sha256Hex(this.password);
        if(this.rentedVehicle != null) {
            return this.login + ";" + encryptedPassword + ";" + this.role + ";" + this.rentedVehicle.plate;
        }
        else return this.login + ";" + encryptedPassword + ";" + this.role;

    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", rentedVehicle=" + rentedVehicle.toString() +
                '}';
    }

    public void addVehicle(VehicleRepository vehicleRepository, Vehicle vehicle) throws IOException {
        if(this.role.equals("Admin")){
            vehicleRepository.addVehicle(vehicle);
        }
        else System.out.println("Not enough permissions");
    }
    public void delVehicle(VehicleRepository vehicleRepository, String plate) throws IOException {
        if(this.role.equals("Admin")) {
            for (Vehicle v : vehicleRepository.vehicles) {
                if (v.plate.equals(plate)) {
                    vehicleRepository.removeVehicle(plate);
                }
            }
        }
        else System.out.println("Not enough permissions");
    }
    public boolean login(String password) throws FileNotFoundException {
        Authentication auth = new Authentication();
        return auth.checkForAuth(this.login,password);
    }
    public void adminBrowseUsers(UserRepository userRepository){
        if(this.role.equals("Admin")) {
            for (User usr : userRepository.users) {
                System.out.println(usr.toString());
            }
        }
    }
    public void userCheckSelf(){
        System.out.println(this.toString());
    }

}
