package com.leandi.phonebook.backend.repository;

import com.leandi.phonebook.backend.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
