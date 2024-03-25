package org.example;

import java.io.IOException;
import java.util.ArrayList;

public interface IUserRepository {
    public ArrayList<User> getUsers();
    public User getUser(String login);
    public void save() throws IOException;
}
