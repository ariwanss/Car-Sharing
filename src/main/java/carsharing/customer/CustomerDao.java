package carsharing.customer;

import java.util.List;
import java.util.Map;

public interface CustomerDao {
    void addCustomer(Customer customer);
    Map<Integer, Customer> getCustomers();
    Customer getCustomer(int id);
    void updateCustomer(Customer customer);
    void deleteCustomer(Customer customer);
}
