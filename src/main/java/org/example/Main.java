package org.example;

import org.apache.commons.codec.digest.DigestUtils;
import org.example.jdbc.JdbcUserRepository;
import org.example.jdbc.JdbcVehicleRepository;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException, SQLException {

        App app = new App();
        app.run();


    }
}