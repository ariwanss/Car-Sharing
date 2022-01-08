package carsharing.company;

import java.util.List;
import java.util.Map;

public interface CompanyDao {
    void addCompany(Company company);
    Map<Integer, Company> getCompanies();
    Company getCompany(int id);
    void updateCompany(Company company);
    void deleteCompany(Company company);
}
