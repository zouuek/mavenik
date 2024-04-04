package org.example;

import org.example.jdbc.JdbcUserRepository;
import org.example.jdbc.JdbcVehicleRepository;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class App {
    public static  User user = null;
    private final Scanner scanner = new Scanner(System.in);
    private final IUserRepository iur = JdbcUserRepository.getInstance();
    private final IVehicleRepository ivr = JdbcVehicleRepository.getInstance();

    public void run() throws SQLException, IOException {
        System.out.println("Login");
        //Authentication.checkForAuth(scanner.nextLine(), scanner.nextLine());
        //iur.addUser(new User("kox123","test","ADMIN", null));
        //Motorcycle motorcycle = new Motorcycle("test","test",1233, 13334, "xdxd","A");
        //ivr.addVehicle(motorcycle);
        //iur.removeUser("lukasz");
        //System.out.println(ivr.getVehicle("Lu1234").model);
        ivr.returnVehicle("LU3000","kox123");
        //ivr.rentVehicle("LU3000","kox123");
        ivr.removeVehicle("xdxd");
    }
}
