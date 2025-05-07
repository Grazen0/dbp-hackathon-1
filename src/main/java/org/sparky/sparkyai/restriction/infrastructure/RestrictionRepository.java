package org.sparky.sparkyai.restriction.infrastructure;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import org.sparky.sparkyai.restriction.domain.Restriction;

public interface RestrictionRepository extends JpaRepository<Restriction, Long> {

    List<Restriction> findByCompanyId(Long id);

}
