package org.example.dao;

import org.example.model.Vehicle;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public interface IVehicleRepository {
    public boolean rentVehicle(String plate, String login) throws IOException;
    public boolean returnVehicle(String plate, String login) throws IOException, SQLException;
    public Collection<Vehicle> getVehicles();
    public void addVehicle(Vehicle vehicle) throws IOException;
    public void removeVehicle(String plate) throws IOException, SQLException;
    public Vehicle getVehicle(String plate) throws SQLException;

}
