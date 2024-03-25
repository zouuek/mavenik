package org.example;

import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
       // Car car = new Car("xd","xd",1000,100,1);
       VehicleRepository vehs = new VehicleRepository(new ArrayList<Vehicle>());
       User user = new User("user", "pass", "user");
       UserRepository userRepository = new UserRepository(new ArrayList<User>());


       //vehs.vehicles.add(car);
       //vehs.vehicles.add(car);
        //
        //vehs.vehicles.add(motorcycle);
        try {
            vehs.save();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}