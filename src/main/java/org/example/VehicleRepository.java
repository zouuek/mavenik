package org.example;

import java.io.*;
import java.util.ArrayList;

public class VehicleRepository implements IVehicleRepository {
    ArrayList<Vehicle> vehicles;
    @Override
    public void rentCar(Integer id) throws IOException {
       for(Vehicle vehs : this.vehicles){
           if (vehs.id==id && !vehs.rented) vehs.rented = true;
       }
        try {
            save();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void returnCar(Integer id) throws IOException {
        for(Vehicle vehs : this.vehicles){
            if (vehs.id==id && vehs.rented) vehs.rented = false;
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
        BufferedWriter writer = new BufferedWriter(new FileWriter("xd.csv"));
        writer.flush();
        writer.write("");
        for(Vehicle vehs : this.vehicles){
            writer.append(vehs.toCSV());
            //writer.append(' ');
        }
        writer.close();
    }

//    public VehicleRepository(ArrayList<Vehicle> vehicles) {
//        this.vehicles = new ArrayList<>();
//
//    }
    public VehicleRepository(ArrayList<Vehicle> vehicles){
        this.vehicles = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("xd.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                //System.out.println("test");
                String[] values = line.split(";");
                if(values[5].equals("Car")){
                    System.out.println("test");
                    Car car = new Car(
                            values[0],
                            values[1],
                            Integer.parseInt(values[2]),
                            Integer.parseInt(values[3]),
                            Integer.parseInt((values[6])
                            ));
                    this.vehicles.add(car);
                    save();
                }
                else {
                    //Motorcycle motorcycle = new Motorcycle(values[0], values[1], Integer.parseInt(values[2]), Integer.parseInt(values[3]), Integer.parseInt((values[7]), );
                    Integer x = Integer.parseInt(values[7]);
                    Motorcycle motorcycle = new Motorcycle(values[0],values[1],Integer.parseInt(values[2]),Integer.parseInt(values[3]),x, values[6]);
                    this.vehicles.add(motorcycle);
                    save();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
