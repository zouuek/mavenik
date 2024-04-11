package org.example;

import org.example.config.HibernateUtil;
import org.example.dao.IUserRepository;
import org.example.dao.IVehicleRepository;
import org.example.dao.hibernate.HibernateUserRepo;
import org.example.dao.hibernate.HibernateVehicleRepo;
import org.example.dao.jdbc.JdbcUserRepository;
import org.example.dao.jdbc.JdbcVehicleRepository;
import org.example.model.Motorcycle;
import org.example.model.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class App {
    public static User user = null;
    private final Scanner scanner = new Scanner(System.in);

    private final IUserRepository iur = HibernateUserRepo.getInstance(HibernateUtil.getSessionFactory());
    private final IVehicleRepository ivr = HibernateVehicleRepo.getInstance(HibernateUtil.getSessionFactory());

    public void run() throws SQLException, IOException {
        System.out.println("Login");

        //Authentication.checkForAuth(scanner.nextLine(), scanner.nextLine());
        //iur.addUser(new User("kox123234","te312st","ADMIN", null));
        //Motorcycle motorcycle = new Motorcycle("tes1t","te12st",1233, 13334, "xdx32d","A");
        //ivr.addVehicle(motorcycle);
        //User user1 = new User("tymek77","g3spt4k", User.Role.ADMIN);
        //ivr.addVehicle();
        //ivr.rentVehicle("Lu1000", "tymek77");
        //ivr.returnVehicle("Lu1000","tymek77");
        ivr.removeVehicle("xdx32d");
        //iur.addUser(user1);
        //iur.removeUser("lukasz");
        //System.out.println(ivr.getVehicle("LU3000").user.login);
        //ivr.rentVehicle("LU3000","kox123");
        //ivr.returnVehicle("LU3000","kox123");
        //ivr.getVehicle("LU3000");
        //ivr.rentVehicle("LU3000","kox123");
        //ivr.removeVehicle("xdxd");
    }
}
