package com.example.demo.company.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.company.domain.Company;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findById(Long id);
}
