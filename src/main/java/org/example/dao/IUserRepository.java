package org.example.dao;

import org.example.model.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public interface IUserRepository {
    Collection<User> getUsers();
    User getUser(String login) throws SQLException;
    void addUser(User user) throws IOException, SQLException;
    void removeUser(String login) throws SQLException;
}
