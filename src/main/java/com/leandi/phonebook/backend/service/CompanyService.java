package com.leandi.phonebook.backend.service;

import com.leandi.phonebook.backend.entity.Company;
import com.leandi.phonebook.backend.entity.Contact;
import com.leandi.phonebook.backend.repository.CompanyRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class CompanyService {

    private static final Logger LOGGER = Logger.getLogger(CompanyService.class.getName());
    private CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public List<Company> findAll() {
        return companyRepository.findAll();
    }
    public void delete(Company company) {
        companyRepository.delete(company);
    }
    public void save(Company company) {
        if (company == null) {
            LOGGER.log(Level.SEVERE,
                    "Contact is null. Are you sure you have connected your form to the application?");
            return;
        }
        companyRepository.save(company);
    }
    public Map<String, Integer> getStats() {
        HashMap<String, Integer> stats = new HashMap<>();
        findAll().forEach(company ->
            stats.put(company.getName(), company.getEmployees().size()));
        return stats;
    }
}
