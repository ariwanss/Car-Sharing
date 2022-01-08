package carsharing.company;

import java.sql.*;
import java.util.*;

public class CompanyDaoImpl implements CompanyDao {
    private String dbName;

    public CompanyDaoImpl(String dbName) {
        this.dbName = dbName;
    }

    @Override
    public void addCompany(Company company) {
        String sql = "INSERT INTO COMPANY(NAME) VALUES('" + company.getName() + "');";

        try (
                Connection conn = connect();
                Statement statement = conn.createStatement()
                ) {
            conn.setAutoCommit(true);
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Map<Integer, Company> getCompanies() {
        String sql = "SELECT * FROM COMPANY";
        Map<Integer, Company> toReturn = new HashMap<>();

        try (
                Connection conn = connect();
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery(sql)
                ) {
            while (resultSet.next()) {
                toReturn.put(resultSet.getInt("ID"), new Company(resultSet.getInt("ID"), resultSet.getString("NAME")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return toReturn;
    }

    @Override
    public Company getCompany(int id) {
        String sql = "SELECT * FROM COMPANY WHERE ID = " + id;

        try (
                Connection conn = connect();
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery(sql)
        ) {
            if (resultSet.next()) {
                return new Company(resultSet.getInt("ID"), resultSet.getString("NAME"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void updateCompany(Company company) {

    }

    @Override
    public void deleteCompany(Company company) {

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
