package carsharing.cars;

import carsharing.company.Company;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CarDaoImpl implements CarDao {

    private String dbName;

    public CarDaoImpl(String dbName) {
        this.dbName = dbName;
    }

    @Override
    public void addCar(Car car) {
        String sql = "INSERT INTO CAR(NAME, COMPANY_ID) VALUES('" +
                car.getName() + "', " + car.getCompanyId() + ");";

        try (
                Connection conn = connect();
                Statement statement = conn.createStatement()
                ) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Car> getCars() {
        String sql = "SELECT * FROM CAR;";
        List<Car> toReturn = new ArrayList<>();

        try (
                Connection conn = connect();
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);
                ) {
            while (resultSet.next()) {
                toReturn.add(new Car(resultSet.getInt("ID"),
                        resultSet.getString("NAME"),
                        resultSet.getInt("COMPANY_ID")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return toReturn;
    }

    @Override
    public Map<Integer, Car> getCarsOf(Company company) {
        String sql = "SELECT * FROM CAR WHERE COMPANY_ID = " + company.getId() + " AND IS_RENTED = FALSE";
        Map<Integer, Car> toReturn = new HashMap<>();
        int i = 1;

        try (
                Connection conn = connect();
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery(sql)
                ) {
            while (resultSet.next()) {
                toReturn.put(i, new Car(resultSet.getInt("ID"),
                        resultSet.getString("NAME"),
                        resultSet.getInt("COMPANY_ID")));
                i += 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return toReturn;
    }

    @Override
    public Car getCar(int id) {
        String sql = "SELECT * FROM CAR WHERE ID = " + id;

        try (
                Connection conn = connect();
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery(sql)
        ) {
            if (resultSet.next()) {
                return new Car(resultSet.getInt("ID"), resultSet.getString("NAME"), resultSet.getInt("COMPANY_ID"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void updateCar(Car car) {
        String sql = "UPDATE CAR SET IS_RENTED = " + car.isRented() + " WHERE ID = " + car.getId() + ";";

        try (
                Connection conn = connect();
                Statement statement = conn.createStatement();
        ) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteCar(Car car) {

    }

    private Connection connect() {
        String url = "jdbc:h2:./src/carsharing/db/" + dbName;
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return conn;
    }
}
