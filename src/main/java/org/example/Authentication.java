package org.example;

import org.apache.commons.codec.digest.DigestUtils;
import org.example.jdbc.JdbcUserRepository;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class Authentication {

//    public Boolean checkForAuth(String login, String password) throws FileNotFoundException {
//        try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\miney\\IdeaProjects\\mavenik\\src\\main\\resources\\users.csv"))) {
//            String line;
//            while ((line = br.readLine()) != null) {
//                String[] values = line.split(";");
//                System.out.println(values[1]+ " " + DigestUtils.sha256Hex(password));
//                if (values[0].equals(login) && values[1].equals(DigestUtils.sha256Hex(password))) return true;
//
//            }
//        } catch (IOException ex) {
//            throw new RuntimeException(ex);
//        }
//        return false;
//    }
public static boolean checkForAuth(String login, String password) throws SQLException {
    JdbcUserRepository jur = JdbcUserRepository.getInstance();
    User userFromDb = jur.getUser(login);
    if ( userFromDb!= null && hashPassword(password).equals(userFromDb.password)) {
        System.out.println("User logged in");
        return true;
    }
    return false;
}
    public static String hashPassword(String password){
        return DigestUtils.sha256Hex(password);
    }


}