package com.example.demo.restriction.infrastructure;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.restriction.domain.Restriction;

public interface RestrictionRepository extends JpaRepository<Restriction, Long> {

    List<Restriction> findByCompanyId(Long id);

}
