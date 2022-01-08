package carsharing.controller;

import carsharing.cars.Car;
import carsharing.cars.CarDao;
import carsharing.company.Company;
import carsharing.company.CompanyDao;
import carsharing.customer.Customer;
import carsharing.customer.CustomerDao;

import java.util.Map;

public class CustomerController extends AbstractController {

    private Customer customer;
    private Car car;

    public CustomerController(CompanyDao companyDao, CustomerDao customerDao, CarDao carDao) {
        super(companyDao, customerDao, carDao);
    }

    @Override
    public void menu() {
        listCustomer();
    }

    public void listCustomer() {
        Map<Integer, Customer> customers = customerDao.getCustomers();

        if (customers.isEmpty()) {
            System.out.println("The customer list is empty!");
            System.out.println();
        } else {
            System.out.println("Customer list:");
            customers.forEach((key, value) -> System.out.println(key + ". " + value));
            System.out.println("0. Back");

            int response = Integer.parseInt(scanner.nextLine());
            System.out.println();

            if (response == 0) {
                return;
            }

            customer = customers.get(response);
            customerMenu();
        }
    }

    public void customerMenu() {
        while (true) {
            System.out.println("1. Rent a car\n" +
                    "2. Return a rented car\n" +
                    "3. My rented car\n" +
                    "0. Back");
            int response = Integer.parseInt(scanner.nextLine());
            System.out.println();

            if (response == 0) {
                return;
            } else if (response == 1) {
                rentCar();
            } else if (response == 2) {
                returnRentedCar();
            } else if (response == 3) {
                getRentedCar();
            }

            System.out.println();
        }
    }

    public void rentCar() {
        if (customer.getRentedCarId() != 0) {
            System.out.println("You've already rented a car!");
        } else {
            listCompanies();

            if (car != null) {
                car.setRented(true);
                customer.setRentedCarId(car.getId());
                customerDao.updateCustomer(customer);
                carDao.updateCar(car);
                System.out.println("You rented '" + car + "'");
            }
        }
    }

    public void returnRentedCar() {
        if (customer.getRentedCarId() == 0) {
            System.out.println("You didn't rent a car!");
        } else {
            car = carDao.getCar(customer.getRentedCarId());
            car.setRented(false);
            customer.setRentedCarId(null);
            customerDao.updateCustomer(customer);
            carDao.updateCar(car);
            System.out.println("You've returned a rented car!");
        }
    }

    public void getRentedCar() {
        //System.out.println(customer.getRentedCarId());
        if (customer.getRentedCarId() == 0) {
            System.out.println("You didn't rent a car!");
        } else {
            Car rentedCar = carDao.getCar(customer.getRentedCarId());
            Company company = companyDao.getCompany(rentedCar.getCompanyId());
            System.out.println("Your rented car:");
            System.out.println(rentedCar);
            System.out.println("Company:");
            System.out.println(company);
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
            listCars(company);
        }
    }

    public void listCars(Company company) {
        Map<Integer, Car> cars = carDao.getCarsOf(company);

        if (cars.isEmpty()) {
            System.out.println("No available cars in the '" + company + "' company");
            car = null;
        } else {
            System.out.println("Choose a car:");
            cars.forEach((key, value) -> System.out.println(key + ". " + value));
            System.out.println("0. Back");

            int response = getResponse();

            if (response == 0) {
                return;
            }

            car = cars.get(response);
        }
    }
}
