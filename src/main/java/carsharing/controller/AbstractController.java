package carsharing.controller;

import carsharing.cars.CarDao;
import carsharing.company.CompanyDao;
import carsharing.customer.CustomerDao;

import java.util.Scanner;

abstract class AbstractController implements Controller {
    protected static final Scanner scanner = new Scanner(System.in);
    public CompanyDao companyDao;
    public CustomerDao customerDao;
    public CarDao carDao;

    public AbstractController(CompanyDao companyDao, CustomerDao customerDao, CarDao carDao) {
        this.companyDao = companyDao;
        this.customerDao = customerDao;
        this.carDao = carDao;
    }

    public abstract void menu();

    public int getResponse() {
        int response = Integer.parseInt(scanner.nextLine());
        System.out.println();
        return response;
    }
}
