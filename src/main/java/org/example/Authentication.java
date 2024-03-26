package org.example;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Authentication {

    public Boolean checkForAuth(String login, String password) throws FileNotFoundException {
        try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\miney\\IdeaProjects\\mavenik\\src\\main\\resources\\users.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                System.out.println(values[1]+ " " + DigestUtils.sha256Hex(password));
                if (values[0].equals(login) && values[1].equals(DigestUtils.sha256Hex(password))) return true;

            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        return false;
    }


}