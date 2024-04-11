package org.example.dao.jdbc;

import org.example.dao.IVehicleRepository;
import org.example.model.Car;
import org.example.model.Motorcycle;
import org.example.model.Vehicle;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class JdbcVehicleRepository implements IVehicleRepository {
    private static JdbcVehicleRepository instance;
    private final DatabaseManager manager;
    private final String GET_ALL_VEHICLE_SQL = "SELECT * FROM tvehicle";
    private final  String GET_VEHICLE_SQL = "SELECT * FROM tvehicle WHERE plate LIKE ?";
    private final String RENT_CAR_SQL = "UPDATE tvehicle SET rent = 1 WHERE plate LIKE ? AND rent = 0";
    private final  String RENT_UPDATE_USER_SQL = "UPDATE tuser SET rentedplate = ? WHERE login LIKE ? AND rentedplate IS NULL";
    private final String INSERT_SQL = "INSERT INTO tvehicle (brand, model, year, price, plate, vehicle_type) VALUES (?,?,?,?,?,?)";
    private final String INSERT_SQL_MOTOR = "INSERT INTO tvehicle (brand, model, year, price, plate, category, vehicle_type) VALUES (?,?,?,?,?,?,?)";
    private final String RETURN_CAR_SQL = "UPDATE tvehicle SET rent = 0 WHERE plate LIKE ? AND rent = 1";
    private final  String RETURN_UPDATE_USER_SQL = "UPDATE tuser SET rentedplate = null WHERE login LIKE ? AND rentedplate LIKE ?";
    private final String REMOVE_VEHICLE_SQL = "DELETE FROM tvehicle WHERE plate LIKE ?";


    public static JdbcVehicleRepository getInstance(){
        if (JdbcVehicleRepository.instance==null){
            instance = new JdbcVehicleRepository();
        }
        return instance;
    }

    private JdbcVehicleRepository() {
        this.manager = DatabaseManager.getInstance();
    }


    @Override
    public boolean rentVehicle(String plate, String login) {
        Connection conn = null;
        PreparedStatement rentCarStmt = null;
        PreparedStatement updateUserStmt = null;

        try {
            conn = manager.getConnection();
            conn.setAutoCommit(false); // reczny commit

            rentCarStmt = conn.prepareStatement(RENT_CAR_SQL);
            rentCarStmt.setString(1, plate);
            int changed1 =rentCarStmt.executeUpdate();

            updateUserStmt = conn.prepareStatement(RENT_UPDATE_USER_SQL);
            updateUserStmt.setString(1, plate);
            updateUserStmt.setString(2, login);
            int changed2 =updateUserStmt.executeUpdate();

            if (changed1 > 0 && changed2 > 0) {
                System.out.println("wypozyczono");
                conn.commit();
            } else {
                System.out.println("Nie wypożyczono");
                conn.rollback(); // wycofuje zmiany
            }

        } catch(Exception e) {
            e.printStackTrace();
            if (conn!= null) {
                try {
                    conn.rollback(); // Wycofuje zmiany
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } finally {
            try { if (rentCarStmt != null) rentCarStmt.close(); } catch (Exception e) {};
            try { if (updateUserStmt != null) updateUserStmt.close(); } catch (Exception e) {};
            try { if (conn != null) conn.close(); } catch (Exception e) {};
        }
        return false;
    }

    @Override
    public boolean returnVehicle(String plate, String login) throws SQLException {

        Connection connection = manager.getConnection();
        connection.setAutoCommit(false);
        JdbcUserRepository jur = JdbcUserRepository.getInstance();
        if(getVehicle(plate).rent && jur.getUser(login).rentedVehicle.plate.equals(plate)) {
            PreparedStatement preparedStatement = connection.prepareStatement(RETURN_CAR_SQL);
            preparedStatement.setString(1,plate);
            int check = preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement(RETURN_UPDATE_USER_SQL);
            preparedStatement.setString(1,login);
            preparedStatement.setString(2,plate);
            int check2 = preparedStatement.executeUpdate();
            if(check > 0 && check2 > 0){
                System.out.println("zwrocono");
                connection.commit();
                preparedStatement.close();
                connection.close();
                return true;
            }
            else{
                System.out.println("nie udalo sie zwrocic");
                connection.rollback();
                preparedStatement.close();
                connection.close();
                return false;
            }
        }
        System.out.println("nie udalo sie zwrocicxd");
        connection.close();

        return false;
    }

    @Override
    public void addVehicle(Vehicle vehicle) {
        if(vehicle.getClass().getSimpleName().equals("Car")) {
            try (Connection conn = manager.getConnection()){
                PreparedStatement stmt = conn.prepareStatement(INSERT_SQL);

                stmt.setString(1, vehicle.brand);
                stmt.setString(2, vehicle.model);
                stmt.setInt(3, vehicle.year);
                stmt.setInt(4, vehicle.price);
                stmt.setString(5, vehicle.plate);
                stmt.setString(6,vehicle.getClass().getSimpleName().toUpperCase());

                int changed = stmt.executeUpdate();

                if (changed > 0) {
                    System.out.println("Pojazd został pomyślnie dodany.");
                    //return true;
                } else {
                    System.out.println("Nie udało się dodać pojazdu.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            //return false;
        }
        else if(vehicle.getClass().getSimpleName().equals("Motorcycle")){
            try (Connection conn = manager.getConnection()){
                 PreparedStatement stmt = conn.prepareStatement(INSERT_SQL_MOTOR);

                stmt.setString(1, vehicle.brand);
                stmt.setString(2, vehicle.model);
                stmt.setInt(3, vehicle.year);
                stmt.setInt(4, vehicle.price);
                stmt.setString(5, vehicle.plate);
                stmt.setString(6, ((Motorcycle) vehicle).category);
                stmt.setString(7, vehicle.getClass().getSimpleName().toUpperCase());

                int changed = stmt.executeUpdate();

                if (changed > 0) {
                    System.out.println("Pojazd został pomyślnie dodany.");
                    //return true;
                } else {
                    System.out.println("Nie udało się dodać pojazdu.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            //return false;
        }
    }

    @Override
    public void removeVehicle(String plate) throws SQLException {
        Connection connection = manager.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(REMOVE_VEHICLE_SQL);
        preparedStatement.setString(1,plate);
        if(!getVehicle(plate).rent) {
            int changed = preparedStatement.executeUpdate();

            if (changed > 0) {
                System.out.println("pojazd został pomyślnie usuniety.");
            }
        }
        else System.out.println("nie udało sie usunac pojazdu");
        connection.close();
        preparedStatement.close();
    }

    @Override
    public Vehicle getVehicle(String vplate) throws SQLException {
        Connection connection = manager.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(GET_VEHICLE_SQL);
        preparedStatement.setString(1,vplate);
        ResultSet rs = preparedStatement.executeQuery();
        rs.next();
        Vehicle v = null;
        String category = rs.getString("category");
        String plate = rs.getString("plate");
        String brand = rs.getString("brand");
        String model = rs.getString("model");
        int year = rs.getInt("year");
        int price = rs.getInt("price");
        boolean rent = rs.getBoolean("rent");
        String type = rs.getString("vehicle_type");

        if ( category!=null){
            v = new Motorcycle(brand,model,year,price,plate,category);
        }else{
            v = new Car(brand,model,year,price,plate);
        }
        v.rent = rent;

        rs.close();
        preparedStatement.close();
        connection.close();
        return v;



    }

    @Override
    public Collection<Vehicle> getVehicles() {

        Collection<Vehicle> vehicles = new ArrayList<>();
        Connection connection = null;
        ResultSet rs = null;
        try {
            connection = manager.getConnection();
            Statement statement = connection.createStatement();
            rs = statement.executeQuery(GET_ALL_VEHICLE_SQL);
            while(rs.next()){
                Vehicle v = null;
                String category = rs.getString("category");
                String plate = rs.getString("plate");
                String brand = rs.getString("brand");
                String model = rs.getString("model");
                int year = rs.getInt("year");
                int price = rs.getInt("price");
                boolean rent = rs.getBoolean("rent");
                String type = rs.getString("vehicle_type");
                if ( category!=null){
                    //Motorcycle(String brand, String model, int year, double price, String plate, String category)
                    v = new Motorcycle(brand,model,year,price,plate,category);

                }else{
                    v = new Car(brand,model,year,price,plate);
                }
                v.rent = rent;
                vehicles.add(v);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            if( rs!=null){try {rs.close();} catch (SQLException e) {e.printStackTrace();}}
            if( connection!=null){try {connection.close();} catch (SQLException e) {e.printStackTrace();}}
        }
        return vehicles;

    }

}