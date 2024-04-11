package org.example.dao.csv;

import org.apache.commons.codec.digest.DigestUtils;
import org.example.utility.Authentication;
import org.example.dao.IUserRepository;
import org.example.model.User;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class CsvUserRepository implements IUserRepository {
    public ArrayList<User> users;
    private static CsvUserRepository instance;

    public static CsvUserRepository getInstance(){
        if(CsvUserRepository.instance == null){
            instance = new CsvUserRepository();
        }
        return instance;
    }

    private CsvUserRepository() {
        this.users = new ArrayList<User>();
        try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\miney\\IdeaProjects\\mavenik\\src\\main\\resources\\users.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                    User user = new User(
                            values[0],
                            values[1],
                            values[2].toUpperCase(),
                            values[3]
                    );
                    this.users.add(user);
                }

            } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Collection<User> getUsers() {
        return this.users;
    }

    @Override
    public User getUser(String login) {
        for (User u : this.users){
            if (u.login.equals(login)) return u;
        }
        return null;
    }
    @Override
    public void addUser(User user) throws IOException {
        user.password = DigestUtils.sha256Hex(user.password);
        this.users.add(user);
        save();
    }

    @Override
    public  void removeUser(String login){
        this.users.remove(this.getUser(login));
        try {
            save();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void save() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\miney\\IdeaProjects\\mavenik\\src\\main\\resources\\users.csv"));
        writer.flush();
        writer.write("");
        for(User u : this.users){
            writer.append(u.toCSV());
            //writer.append(' '); xddd
        }
        writer.close();
    }
    void logIn(String login, String password) throws FileNotFoundException, SQLException {
        if(Authentication.checkForAuth(login,password)){
            System.out.println(login + " has logged in.\n");

        }
        else System.out.println("Wrong credentials!\n");
    }

}

