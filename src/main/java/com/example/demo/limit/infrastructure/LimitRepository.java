package com.example.demo.limit.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.limit.domain.Limit;

public interface LimitRepository extends JpaRepository<Limit, Long> {
}
