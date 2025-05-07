package org.sparky.sparkyai.user.infrastructure;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
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
    private User admin1;
    private User admin2;
    private User user1;
    private User user2;
    private User user3;

    @BeforeEach
    public void setup() {
        admin1 = new User();
        admin1.setUsername("admin1");
        admin1.setPassword("123456");
        admin1.setRole(Role.COMPANY_ADMIN);
        userRepository.save(admin1);

        company1 = new Company();
        company1.setName("ABC");
        company1.setRuc("1029384756");
        company1.setAdmin(admin1);
        entityManager.persist(company1);

        admin2 = new User();
        admin2.setUsername("admin2");
        admin2.setPassword("123456");
        admin2.setRole(Role.COMPANY_ADMIN);
        userRepository.save(admin2);

        company2 = new Company();
        company2.setName("DEF");
        company2.setRuc("5647382910");
        company2.setAdmin(admin2);
        entityManager.persist(company2);

        user1 = new User();
        user1.setUsername("john");
        user1.setPassword("123456");
        user1.setRole(Role.USER);
        user1.setCompany(company1);
        userRepository.save(user1);

        user2 = new User();
        user2.setUsername("juan");
        user2.setPassword("123456");
        user2.setRole(Role.USER);
        user2.setCompany(company1);
        userRepository.save(user2);

        user3 = new User();
        user3.setUsername("pepe");
        user3.setPassword("123456");
        user3.setRole(Role.USER);
        user3.setCompany(company2);
        userRepository.save(user3);

        entityManager.flush();
    }

    @Test
    public void testFindByUsername() {
        Optional<User> result1 = userRepository.findByUsername(admin1.getUsername());
        Optional<User> result2 = userRepository.findByUsername(admin2.getUsername());
        Optional<User> result3 = userRepository.findByUsername(user1.getUsername());
        Optional<User> result4 = userRepository.findByUsername(user2.getUsername());
        Optional<User> result5 = userRepository.findByUsername(user3.getUsername());
        Optional<User> result6 = userRepository.findByUsername("carlos");
        Optional<User> result7 = userRepository.findByUsername("");

        assertTrue(result1.isPresent());
        assertTrue(result2.isPresent());
        assertTrue(result3.isPresent());
        assertTrue(result4.isPresent());
        assertTrue(result5.isPresent());
        assertTrue(result6.isEmpty());
        assertTrue(result7.isEmpty());

        assertEquals(result1.get().getUsername(), admin1.getUsername());
        assertEquals(result2.get().getUsername(), admin2.getUsername());
        assertEquals(result3.get().getUsername(), user1.getUsername());
        assertEquals(result4.get().getUsername(), user2.getUsername());
        assertEquals(result5.get().getUsername(), user3.getUsername());
    }

    @Test
    public void testFindByIdAndCompanyId() {
        Optional<User> result1 = userRepository.findByIdAndCompanyId(user1.getId(), company1.getId());
        Optional<User> result2 = userRepository.findByIdAndCompanyId(user2.getId(), company1.getId());
        Optional<User> result3 = userRepository.findByIdAndCompanyId(user3.getId(), company2.getId());
        Optional<User> result4 = userRepository.findByIdAndCompanyId(user1.getId(), company2.getId());
        Optional<User> result5 = userRepository.findByIdAndCompanyId(user3.getId(), company1.getId());

        assertTrue(result1.isPresent());
        assertTrue(result2.isPresent());
        assertTrue(result3.isPresent());
        assertTrue(result4.isEmpty());
        assertTrue(result5.isEmpty());

        assertEquals(result1.get().getUsername(), user1.getUsername());
        assertEquals(result2.get().getUsername(), user2.getUsername());
        assertEquals(result3.get().getUsername(), user3.getUsername());
    }

    @Test
    public void testFindByCompanyId() {
        List<User> result1 = userRepository.findByCompanyId(company1.getId());
        List<User> result2 = userRepository.findByCompanyId(company2.getId());

        assertEquals(result1.size(), 2);
        assertEquals(result2.size(), 1);
    }

    @Test
    public void testExistsByUsername() {
        boolean result1 = userRepository.existsByUsername(admin1.getUsername());
        boolean result2 = userRepository.existsByUsername(admin2.getUsername());
        boolean result3 = userRepository.existsByUsername(user1.getUsername());
        boolean result4 = userRepository.existsByUsername(user2.getUsername());
        boolean result5 = userRepository.existsByUsername(user3.getUsername());
        boolean result6 = userRepository.existsByUsername("");
        boolean result7 = userRepository.existsByUsername("carlos");

        assertTrue(result1);
        assertTrue(result2);
        assertTrue(result3);
        assertTrue(result4);
        assertTrue(result5);
        assertFalse(result6);
        assertFalse(result7);
    }

}
