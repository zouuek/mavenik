package org.example;

import java.io.*;
import java.util.ArrayList;

public class VehicleRepository implements IVehicleRepository {
    ArrayList<Vehicle> vehicles;
    @Override
    public void rentCar(String plate) throws IOException {
       for(Vehicle vehs : this.vehicles){
           if (vehs.plate.equals(plate) && !vehs.rented) vehs.rented = true;
       }
        try {
            save();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void returnCar(String plate) throws IOException {
        for(Vehicle vehs : this.vehicles){
            if (vehs.plate.equals(plate) && vehs.rented) vehs.rented = false;
        }
        try {
            save();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ArrayList<Vehicle> getVehicles() {
        return this.vehicles;
    }
    public void save() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\miney\\IdeaProjects\\mavenik\\src\\main\\resources\\xd.csv"));
        writer.flush();
        writer.write("");
        for(Vehicle vehs : this.vehicles){
            writer.append(vehs.toCSV());
        }
        writer.close();
    }
    public VehicleRepository(ArrayList<Vehicle> vehicles){
        this.vehicles = vehicles;
        try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\miney\\IdeaProjects\\mavenik\\src\\main\\resources\\xd.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                if(values[5].equals("Car")){
                    Car car = new Car(
                            values[0],
                            values[1],
                            Integer.parseInt(values[2]),
                            Integer.parseInt(values[3]),
                            values[6]
                            );
                    this.vehicles.add(car);
                    save();
                }
                else {
                    Motorcycle motorcycle = new Motorcycle(
                            values[0],
                            values[1],
                            Integer.parseInt(values[2]),
                            Integer.parseInt(values[3]),
                            values[7],
                            values[6]);
                    this.vehicles.add(motorcycle);
                    save();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void addVehicle(Vehicle vehicle) throws IOException {
        for (Vehicle vehs : this.vehicles){
            if (vehs.plate.equals(vehicle.plate)) return;
        }
        this.vehicles.add(vehicle);
        save();
    }

    @Override
    public void removeVehicle(String plate) throws IOException {
        for (Vehicle vehs : this.vehicles){
            if (vehs.plate.equals(plate)) this.vehicles.remove(vehs);
        }
        save();
    }

    @Override
    public Vehicle getVehicle(String plate) {
        for (Vehicle vehs : this.vehicles){
            if (vehs.plate.equals(plate)) return vehs;
        }
        return null;
    }

    public void userRentVehicle(User user, String plate){
        for (Vehicle vehs : this.vehicles){
            if (vehs.plate.equals(plate)) user.rentVehicle(vehs);
        }
    }
    public void userDeRentVehicle(User user, String plate){
        for (Vehicle vehs : this.vehicles){
            if (vehs.plate.equals(plate)) user.deRentVehicle(vehs);
        }
    }


}
