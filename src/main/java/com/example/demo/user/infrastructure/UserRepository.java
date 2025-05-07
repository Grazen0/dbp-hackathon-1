package com.example.demo.user.infrastructure;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.user.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByIdAndCompanyId(Long id, Long comapnyId);

    List<User> findByCompanyId(Long id);
}
