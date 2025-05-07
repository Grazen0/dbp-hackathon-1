package org.sparky.sparkyai.limit.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import org.sparky.sparkyai.limit.domain.Limit;

public interface LimitRepository extends JpaRepository<Limit, Long> {
}
