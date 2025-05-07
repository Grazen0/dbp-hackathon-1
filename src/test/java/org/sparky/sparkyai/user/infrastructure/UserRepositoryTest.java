package org.sparky.sparkyai.user.infrastructure;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sparky.sparkyai.common.infrastructure.ContainerTestBase;
import org.sparky.sparkyai.company.domain.Company;
import org.sparky.sparkyai.user.domain.Role;
import org.sparky.sparkyai.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest extends ContainerTestBase {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    private Company company1;
    private Company company2;

    @BeforeEach
    public void setup() {
        User admin1 = new User();
        admin1.setUsername("admin1");
        admin1.setPassword("123456");
        admin1.setRole(Role.COMPANY_ADMIN);
        userRepository.save(admin1);

        company1 = new Company();
        company1.setName("ABC");
        company1.setRuc("1029384756");
        company1.setAdmin(admin1);
        entityManager.persist(company1);

        User admin2 = new User();
        admin2.setUsername("admin2");
        admin2.setPassword("123456");
        admin2.setRole(Role.COMPANY_ADMIN);
        userRepository.save(admin2);

        company2 = new Company();
        company2.setName("DEF");
        company2.setRuc("5647382910");
        company2.setAdmin(admin2);
        entityManager.persist(company2);

        entityManager.flush();
    }

    @Test
    public void testFindByUsername() {
        Optional<User> result1 = userRepository.findByUsername("admin1");
        Optional<User> result2 = userRepository.findByUsername("admin2");
        Optional<User> result3 = userRepository.findByUsername("juan");

        assertTrue(result1.isPresent());
        assertTrue(result2.isPresent());
        assertTrue(result3.isEmpty());

        assertEquals(result1.get().getUsername(), "admin1");
        assertEquals(result2.get().getUsername(), "admin2");
    }

}
