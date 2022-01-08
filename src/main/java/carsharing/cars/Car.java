package carsharing.cars;

public class Car {

    private int id;
    private String name;
    private int companyId;
    private boolean rented;

    public Car(int id, String name, int companyId) {
        this.id = id;
        this.name = name;
        this.companyId = companyId;
    }

    public Car(String name, int companyId) {
        this.name = name;
        this.companyId = companyId;
    }

    public int getId() {
        return id;
    }

    public void setRented(boolean rented) {
        this.rented = rented;
    }

    public boolean isRented() {
        return rented;
    }

    public String getName() {
        return name;
    }

    public int getCompanyId() {
        return companyId;
    }

    @Override
    public String toString() {
        return name;
    }
}
