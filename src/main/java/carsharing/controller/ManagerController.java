package carsharing.controller;

import carsharing.cars.Car;
import carsharing.cars.CarDao;
import carsharing.company.Company;
import carsharing.company.CompanyDao;
import carsharing.customer.CustomerDao;

import java.util.Map;

public class ManagerController extends AbstractController {

    public ManagerController(CompanyDao companyDao, CustomerDao customerDao, CarDao carDao) {
        super(companyDao, customerDao, carDao);
    }

    public void menu() {
        while (true) {
            System.out.println("1. Company list\n" +
                    "2. Create a company\n" +
                    "0. Back");

            int response = getResponse();

            if (response == 0) {
                return;
            } else if (response == 1) {
                listCompanies();
            } else if (response == 2) {
                createCompany();
            }

            System.out.println();
        }
    }

    public void listCompanies() {
        Map<Integer, Company> companies = companyDao.getCompanies();

        if (companies.isEmpty()) {
            System.out.println("The company list is empty!");
        } else {
            System.out.println("Choose a company:");
            companies.forEach((key, value) -> System.out.println(key + ". " + value));
            System.out.println("0. Back");

            int response = getResponse();

            if (response == 0) {
                return;
            }

            Company company = companies.get(response);
            companyMenu(company);
        }
    }

    public void companyMenu(Company company) {
        while (true) {
            System.out.println(company.getName() + " company:\n" +
                    "1. Car list\n" +
                    "2. Create a car\n" +
                    "0. Back");

            int response = getResponse();

            if (response == 0) {
                return;
            } else if (response == 1) {
                listCars(company);
            } else if (response == 2) {
                createCar(company.getId());
            }

            System.out.println();
        }
    }

    public void listCars(Company company) {
        Map<Integer, Car> cars = carDao.getCarsOf(company);

        if (cars.isEmpty()) {
            System.out.println("The car list is empty!");
        } else {
            System.out.println("Car list:");
            cars.forEach((key, value) -> System.out.println(key + ". " + value));
        }
    }

    public void createCar(int companyId) {
        System.out.println("Enter the car name:");
        String name = scanner.nextLine();

        carDao.addCar(new Car(name, companyId));
        System.out.println("The car was added!");
    }

    public void createCompany() {
        System.out.println("Enter the company name:");
        String name = scanner.nextLine();

        companyDao.addCompany(new Company(name));
        System.out.println("The company was created!");
    }
}
