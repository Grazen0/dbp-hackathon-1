package org.sparky.sparkyai.restriction.infrastructure;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sparky.sparkyai.common.infrastructure.ContainerTestBase;
import org.sparky.sparkyai.company.domain.Company;
import org.sparky.sparkyai.restriction.domain.Restriction;
import org.sparky.sparkyai.user.domain.Role;
import org.sparky.sparkyai.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RestrictionRepositoryTest extends ContainerTestBase {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RestrictionRepository restrictionRepository;

    private Company company1;
    private Company company2;

    @BeforeEach
    public void setup() {
        User admin1 = new User();
        admin1.setUsername("admin1");
        admin1.setPassword("123456");
        admin1.setRole(Role.COMPANY_ADMIN);
        entityManager.persist(admin1);

        company1 = new Company();
        company1.setName("ABC");
        company1.setRuc("1029384756");
        company1.setAdmin(admin1);
        entityManager.persist(company1);

        User admin2 = new User();
        admin2.setUsername("admin2");
        admin2.setPassword("123456");
        admin2.setRole(Role.COMPANY_ADMIN);
        entityManager.persist(admin2);

        company2 = new Company();
        company2.setName("DEF");
        company2.setRuc("5647382910");
        company2.setAdmin(admin2);
        entityManager.persist(company2);

        entityManager.flush();
    }

    @Test
    public void testFindByCompanyId() {
        Restriction r1 = new Restriction();
        r1.setCompany(company1);
        restrictionRepository.save(r1);

        Restriction r2 = new Restriction();
        r2.setCompany(company1);
        restrictionRepository.save(r2);

        Restriction r3 = new Restriction();
        r3.setCompany(company2);
        restrictionRepository.save(r3);

        List<Restriction> result1 = restrictionRepository.findByCompanyId(company1.getId());
        List<Restriction> result2 = restrictionRepository.findByCompanyId(company2.getId());

        assertEquals(result1.size(), 2);
        assertEquals(result2.size(), 1);
    }

}
