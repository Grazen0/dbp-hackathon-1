package org.sparky.sparkyai.usercall.infrastructure;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sparky.sparkyai.company.domain.Company;
import org.sparky.sparkyai.user.domain.Role;
import org.sparky.sparkyai.user.domain.User;
import org.sparky.sparkyai.usercall.domain.UserCall;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UserCallRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserCallRepository userCallRepository;

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
        entityManager.persist(admin1);

        company1 = new Company();
        company1.setName("ABC");
        company1.setRuc("1029384756");
        company1.setAdmin(admin1);
        entityManager.persist(company1);

        admin2 = new User();
        admin2.setUsername("admin2");
        admin2.setPassword("123456");
        admin2.setRole(Role.COMPANY_ADMIN);
        entityManager.persist(admin2);

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
        entityManager.persist(user1);

        user2 = new User();
        user2.setUsername("juan");
        user2.setPassword("123456");
        user2.setRole(Role.USER);
        user2.setCompany(company1);
        entityManager.persist(user2);

        user3 = new User();
        user3.setUsername("pepe");
        user3.setPassword("123456");
        user3.setRole(Role.USER);
        user3.setCompany(company2);
        entityManager.persist(user3);

        entityManager.flush();
    }

    @Test
    public void testFindByCompanyId() {
        UserCall c1 = new UserCall();
        c1.setUser(user1);
        c1.setCompany(company1);
        c1.setPrompt("hello");
        c1.setResponse("hello back");
        c1.setWasError(false);
        c1.setConsumedTokens(6);
        userCallRepository.save(c1);

        UserCall c2 = new UserCall();
        c2.setUser(user2);
        c2.setCompany(company2);
        c2.setPrompt("hello again");
        c2.setResponse("hello back again");
        c2.setWasError(false);
        c2.setConsumedTokens(8);
        userCallRepository.save(c2);

        UserCall c3 = new UserCall();
        c3.setUser(user3);
        c3.setCompany(company1);
        c3.setPrompt("hello again again");
        c3.setResponse("hello back again again");
        c3.setWasError(true);
        c3.setConsumedTokens(10);
        userCallRepository.save(c3);

        List<UserCall> result1 = userCallRepository.findByCompanyId(company1.getId());
        List<UserCall> result2 = userCallRepository.findByCompanyId(company2.getId());

        assertEquals(result1.size(), 2);
        assertEquals(result2.size(), 1);

        assertEquals(result1.get(0), c1);
        assertEquals(result1.get(1), c3);
        assertEquals(result2.get(0), c2);
    }
}
