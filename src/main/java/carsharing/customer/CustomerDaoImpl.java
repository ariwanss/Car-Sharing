package carsharing.customer;

import carsharing.company.Company;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerDaoImpl implements CustomerDao {

    private String dbName;

    public CustomerDaoImpl(String dbName) {
        this.dbName = dbName;
    }

    @Override
    public void addCustomer(Customer customer) {
        String sql = "INSERT INTO CUSTOMER(NAME) VALUES('" + customer.getName() + "');";

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
    public Map<Integer, Customer> getCustomers() {
        String sql = "SELECT * FROM CUSTOMER;";
        Map<Integer, Customer> toReturn = new HashMap<>();

        try (
                Connection conn = connect();
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery(sql)
                ) {
            while (resultSet.next()) {
                toReturn.put(resultSet.getInt("ID"), new Customer(resultSet.getInt("ID"),
                        resultSet.getString("NAME"),
                        resultSet.getInt("RENTED_CAR_ID")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return toReturn;
    }

    @Override
    public Customer getCustomer(int id) {
        String sql = "SELECT * FROM CUSTOMER WHERE ID = " + id;

        try (
                Connection conn = connect();
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery(sql)
        ) {
            if (resultSet.next()) {
                return new Customer(resultSet.getInt("ID"), resultSet.getString("NAME"), resultSet.getInt("RENTED_CAR_ID"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void updateCustomer(Customer customer) {
        String sql = "UPDATE CUSTOMER SET RENTED_CAR_ID = " + customer.getRentedCarId() +
                " WHERE ID = " + customer.getId() + ";";

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
    public void deleteCustomer(Customer customer) {

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
