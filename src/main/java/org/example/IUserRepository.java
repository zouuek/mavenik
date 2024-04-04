package org.example;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public interface IUserRepository {
    ArrayList<User> getUsers();
    User getUser(String login) throws SQLException;
    void addUser(User user) throws IOException, SQLException;
    void removeUser(String login) throws SQLException;
}
