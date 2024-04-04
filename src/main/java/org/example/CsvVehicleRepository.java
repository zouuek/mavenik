package org.example;

import java.io.*;
import java.util.ArrayList;

public class CsvVehicleRepository implements IVehicleRepository {
    private static CsvVehicleRepository instance;
    public ArrayList<Vehicle> vehicles;
    public static CsvVehicleRepository getInstance(){
        if(CsvVehicleRepository.instance == null){
            instance = new CsvVehicleRepository();
        }
        return instance;
    }
    @Override
    public boolean rentVehicle(String plate, String login) throws IOException {
        Vehicle vehicle = this.getVehicle(plate);
        CsvUserRepository csvUserRepository = CsvUserRepository.getInstance();
        User user = csvUserRepository.getUser(login);

        if(user.rentedVehicle == null && !vehicle.rented){
           vehicle.rented = true;
           user.setRentedVehicle(plate);

        }
        else return false;
        try {
            save();
            csvUserRepository.save();
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean returnVehicle(String plate, String login) throws IOException {
        Vehicle vehicle = this.getVehicle(plate);
        CsvUserRepository csvUserRepository = CsvUserRepository.getInstance();
        User user = csvUserRepository.getUser(login);

        if(user.rentedVehicle != null && vehicle.rented){
            vehicle.rented = false;
            user.rentedVehicle = null;
        }

        try {
            save();
            csvUserRepository.save();
            return true;
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
    public CsvVehicleRepository(){
        this.vehicles = new ArrayList<Vehicle>();
        try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\miney\\IdeaProjects\\mavenik\\src\\main\\resources\\xd.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                if(values.length > 1 && values[5].equals("Car")){   // nie wiem czemu sie tak buguje
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
                else if (values.length > 1){
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




}
