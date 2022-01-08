package carsharing;

import carsharing.cars.Car;
import carsharing.cars.CarDao;
import carsharing.cars.CarDaoImpl;
import carsharing.company.Company;
import carsharing.company.CompanyDao;
import carsharing.company.CompanyDaoImpl;
import carsharing.controller.Controller;
import carsharing.controller.CustomerController;
import carsharing.controller.ManagerController;
import carsharing.customer.Customer;
import carsharing.customer.CustomerDao;
import carsharing.customer.CustomerDaoImpl;

import java.sql.*;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class CarSharing {

    private static final Scanner scanner = new Scanner(System.in);
    private CompanyDao companyDao;
    private CarDao carDao;
    private CustomerDao customerDao;
    private String dbName;
    private String url;
    private Controller managerController;
    private Controller customerController;

    public CarSharing(String dbName) {
        this.dbName = dbName;
        this.url = "jdbc:h2:./src/carsharing/db/" + dbName;
        initDatabase();
        this.companyDao = new CompanyDaoImpl(dbName);
        this.carDao = new CarDaoImpl(dbName);
        this.customerDao = new CustomerDaoImpl(dbName);
        this.managerController = new ManagerController(companyDao, customerDao, carDao);
        this.customerController = new CustomerController(companyDao, customerDao, carDao);

    }

    public void run() {
        Controller controller;
        while (true) {
            System.out.println("1. Log in as a manager\n" +
                    "2. Log in as a customer\n" +
                    "3. Create a customer\n" +
                    "0. Exit");

            int response = Integer.parseInt(scanner.nextLine());
            System.out.println();

            if (response == 0) {
                return;
            } else if (response == 1) {
                controller = managerController;
                controller.menu();
            } else if (response == 2) {
                controller = customerController;
                controller.menu();
            } else if (response == 3) {
                createCustomer();
            }

            //System.out.println();
        }
    }

    public void createCustomer() {
        System.out.println("Enter the customer name:");
        String name = scanner.nextLine();

        customerDao.addCustomer(new Customer(name));
        System.out.println("The customer was added!");
    }

    public void initDatabase() {
        createDatabase();
        createCompanyTable();
        createCarTable();
        createCustomerTable();
    }

    public void createDatabase() {
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData metaData = conn.getMetaData();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection connect() {
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return conn;
    }

    public void createCompanyTable() {
        String sql = //"DROP TABLE IF EXISTS COMPANY;" +
                "CREATE TABLE " +
                        "IF NOT EXISTS " +
                        "COMPANY" +
                        "(" +
                        "ID INT PRIMARY KEY AUTO_INCREMENT," +
                        "NAME VARCHAR UNIQUE NOT NULL" +
                        ");" +
                        "ALTER TABLE COMPANY ALTER COLUMN ID RESTART WITH 1;" +
                        "";

        try (
                Connection conn = connect();
                Statement statement = conn.createStatement()
                ) {
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void createCarTable() {
        String sql = //"DROP TABLE IF EXISTS CAR;" +
                "CREATE TABLE " +
                        "IF NOT EXISTS " +
                        "CAR(" +
                        "ID INT PRIMARY KEY AUTO_INCREMENT," +
                        "NAME VARCHAR UNIQUE NOT NULL," +
                        "COMPANY_ID INT NOT NULL," +
                        "IS_RENTED BOOLEAN DEFAULT FALSE," +
                        "FOREIGN KEY(COMPANY_ID) REFERENCES COMPANY(ID)" +
                        ");" +
                        //"ALTER TABLE CAR ALTER COLUMN ID RESTART WITH 1;" +
                        "";

        try (
                Connection conn = connect();
                Statement statement = conn.createStatement()
                ) {
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createCustomerTable() {
        String sql = //"DROP TABLE IF EXISTS CUSTOMER;" +
                "CREATE TABLE " +
                        "IF NOT EXISTS " +
                        "CUSTOMER(" +
                        "ID INT PRIMARY KEY AUTO_INCREMENT," +
                        "NAME VARCHAR UNIQUE NOT NULL," +
                        "RENTED_CAR_ID INT," +
                        "FOREIGN KEY(RENTED_CAR_ID) REFERENCES CAR(ID)" +
                        ");" +
                        "ALTER TABLE CUSTOMER ALTER COLUMN ID RESTART WITH 1;" +
                        "";

        try (
                Connection conn = connect();
                Statement statement = conn.createStatement()
        ) {
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
