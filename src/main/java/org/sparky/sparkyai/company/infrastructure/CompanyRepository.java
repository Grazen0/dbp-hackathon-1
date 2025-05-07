package org.sparky.sparkyai.company.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import org.sparky.sparkyai.company.domain.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
