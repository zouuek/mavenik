package org.example;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
       // Car car = new Car("xd","xd",1000,100,1);
       VehicleRepository vehs = new VehicleRepository(new ArrayList<Vehicle>());

       UserRepository userRepository = new UserRepository(new ArrayList<User>());
       User user = userRepository.getUser("user");
        System.out.println(user.login("pass"));
       String xd = "pass";
       DigestUtils.sha256Hex(xd);
       String xd2 = "pass";
        DigestUtils.sha256Hex(xd2);
        System.out.println(DigestUtils.sha256Hex(xd)+ " `````` " + DigestUtils.sha256Hex(xd2));

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