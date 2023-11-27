package vn.edu.iuh.fit.www_lab05.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.www_lab05.backend.models.Company;
import vn.edu.iuh.fit.www_lab05.backend.repositories.CompanyRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    @Autowired
    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    public Optional<Company> getCompanyById(Long id) {
        return companyRepository.findById(id);
    }

    public void saveCompany(Company company) {
        companyRepository.save(company);
    }

    public void updateCompany(Company company) {
        if (companyRepository.existsById(company.getId())) {
            companyRepository.save(company);
        } else {
            throw new IllegalArgumentException("Company not found with ID: " + company.getId());
        }
    }

    public void deleteCompany(Long id) {
        companyRepository.deleteById(id);
    }
}
