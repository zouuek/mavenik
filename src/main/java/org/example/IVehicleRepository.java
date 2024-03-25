package org.example;

import java.io.IOException;
import java.util.ArrayList;

public interface IVehicleRepository {
    public void rentCar(String plate) throws IOException;
    public void returnCar(String plate) throws IOException;
    public ArrayList<Vehicle> getVehicles();
    public void addVehicle(Vehicle vehicle) throws IOException;
    public void removeVehicle(String plate) throws IOException;
    public Vehicle getVehicle(String plate);

}
