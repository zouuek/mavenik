package org.example;

import java.io.*;
import java.util.ArrayList;

public class UserRepository implements IUserRepository {
    public ArrayList<User> users;

    public UserRepository(ArrayList<User> users) {
        this.users = users;
        try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\miney\\IdeaProjects\\mavenik\\src\\main\\resources\\users.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                    User user = new User(
                            values[0],
                            values[1],
                            values[2]
                    );
                    this.users.add(user);
                    //save();
                }

            } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public ArrayList<User> getUsers() {
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
    void logIn(String login, String password) throws FileNotFoundException {
        Authentication authentication = new Authentication();

        if(authentication.checkForAuth(login,password)){
            System.out.println(login + " has logged in.\n");

        }
        else System.out.println("Wrong credentials!\n");
    }

}

