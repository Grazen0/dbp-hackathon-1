package org.sparky.sparkyai.company.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import org.sparky.sparkyai.company.domain.Company;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findById(Long id);
}
