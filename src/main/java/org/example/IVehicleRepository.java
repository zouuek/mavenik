package org.example;

import java.io.IOException;
import java.util.ArrayList;

public interface IVehicleRepository {
    public void rentCar(Integer id) throws IOException;
    public void returnCar(Integer id) throws IOException;
    public ArrayList<Vehicle> getVehicles();

}
