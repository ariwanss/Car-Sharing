package carsharing.cars;

import carsharing.company.Company;

import java.util.List;
import java.util.Map;

public interface CarDao {
    void addCar(Car car);
    List<Car> getCars();
    Map<Integer, Car> getCarsOf(Company company);
    Car getCar(int id);
    void updateCar(Car car);
    void deleteCar(Car car);
}
