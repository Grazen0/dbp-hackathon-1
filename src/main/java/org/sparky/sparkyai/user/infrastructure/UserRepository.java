package org.sparky.sparkyai.user.infrastructure;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import org.sparky.sparkyai.user.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByIdAndCompanyId(Long id, Long companyId);

    List<User> findByCompanyId(Long id);

    boolean existsByUsername(String username);
}
