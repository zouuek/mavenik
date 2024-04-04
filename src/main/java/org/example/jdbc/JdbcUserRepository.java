package org.example.jdbc;

import org.example.Authentication;
import org.example.IUserRepository;
import org.example.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class JdbcUserRepository implements IUserRepository {
    private static JdbcUserRepository instance;
    private final DatabaseManager databaseManager;

    private static String GET_ALL_USERS_SQL = "SELECT login, password, role, rentedPlate FROM tuser";
    private static String GET_USER_SQL = "SELECT * FROM tuser WHERE login LIKE ?";
    private static String ADD_USER_SQL = "INSERT INTO tuser (login, password, rentedplate, role) VALUES(?,?,?,?)";
    private static String REMOVE_USER_SQL = "DELETE FROM tuser WHERE login LIKE ?";

    private JdbcUserRepository() {
        this.databaseManager = DatabaseManager.getInstance();
    }


    @Override
    public User getUser(String login) throws SQLException {
        User user = null;
        Connection connection =null;
        ResultSet rs = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = databaseManager.getConnection();
            connection.createStatement();
            preparedStatement = connection.prepareStatement(GET_USER_SQL);
            preparedStatement.setString(1, login);
            rs = preparedStatement.executeQuery();
            if(rs.next()) {
                user = new User(
                        rs.getString("login"),
                        rs.getString("password"),
                        rs.getString("role"),
                        rs.getString("rentedPlate")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        finally {
            try { if (preparedStatement != null) preparedStatement.close(); } catch (Exception e) {};
            try { if (rs != null) rs.close(); } catch (Exception e) {};
            try { if (connection != null) connection.close(); } catch (Exception e) {};

        }
        return user;
    }

    @Override
    public void addUser(User user) throws SQLException {
        Connection connection = databaseManager.getConnection();
        connection.createStatement();
        PreparedStatement preparedStatement = connection.prepareStatement(ADD_USER_SQL);
        preparedStatement.setString(1, user.login);
        preparedStatement.setString(2, Authentication.hashPassword(user.password));
        preparedStatement.setString(3, user.rentedVehicle);
        preparedStatement.setString(4, user.role.name());
        int changed = preparedStatement.executeUpdate();
        if (changed  > 0) {
            System.out.println("użytkownik został pomyślnie dodany.");
        }
        else System.out.println("nie udało sie dodac uzytkownika");
        connection.close();
        preparedStatement.close();
    }

    @Override
    public void removeUser(String login) throws SQLException {
        Connection connection = databaseManager.getConnection();
        connection.createStatement();
        PreparedStatement preparedStatement = connection.prepareStatement(REMOVE_USER_SQL);
        preparedStatement.setString(1, login);
        if(getUser(login).rentedVehicle == null) {
            int changed = preparedStatement.executeUpdate();

            if (changed > 0) {
                System.out.println("użytkownik został pomyślnie usuniety.");
            }
        }
        else System.out.println("nie udało sie usunac uzytkownika");
        connection.close();
        preparedStatement.close();
    }

    @Override
    public ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<>();
        try(Connection connection = databaseManager.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(GET_ALL_USERS_SQL)) {
            while(resultSet.next()){
                String login = resultSet.getString("login");
                String password = resultSet.getString("password");
                User.Role role = User.Role.valueOf(resultSet.getString("role"));
                String plate = resultSet.getString("rentedPlate");

                User user = new User(login, password, role.name(),plate);
                users.add(user);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return users;
    }


    public static JdbcUserRepository getInstance(){
        if (JdbcUserRepository.instance == null){
            JdbcUserRepository.instance = new JdbcUserRepository();
        }
        return instance;
    }

}