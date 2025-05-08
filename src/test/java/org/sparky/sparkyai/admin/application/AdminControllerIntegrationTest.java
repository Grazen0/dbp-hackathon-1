package org.sparky.sparkyai.admin.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sparky.sparkyai.common.infrastructure.ContainerTestBase;
import org.sparky.sparkyai.company.domain.Company;
import org.sparky.sparkyai.company.infrastructure.CompanyRepository;
import org.sparky.sparkyai.jwt.domain.JwtService;
import org.sparky.sparkyai.user.domain.Role;
import org.sparky.sparkyai.user.domain.User;
import org.sparky.sparkyai.user.infrastructure.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import jakarta.transaction.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class AdminControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private JwtService jwtService;

    private String authHeader;

    private Company company1;
    private Company company2;

    @BeforeEach
    public void setup() {
        User companyAdmin1 = new User();
        companyAdmin1.setUsername("admin1");
        companyAdmin1.setPassword("123456");
        companyAdmin1.setRole(Role.COMPANY_ADMIN);
        userRepository.save(companyAdmin1);

        company1 = new Company();
        company1.setName("ABC");
        company1.setRuc("5647382910");
        company1.setAdmin(companyAdmin1);
        companyRepository.save(company1);

        User companyAdmin2 = new User();
        companyAdmin2.setUsername("admin2");
        companyAdmin2.setPassword("123456");
        companyAdmin2.setRole(Role.COMPANY_ADMIN);
        userRepository.save(companyAdmin2);

        company2 = new Company();
        company2.setName("DEF");
        company2.setRuc("123456789");
        company2.setAdmin(companyAdmin2);
        companyRepository.save(company2);

        User sparkyAdmin = new User();
        sparkyAdmin.setUsername("sparky_admin");
        sparkyAdmin.setPassword("123456");
        sparkyAdmin.setRole(Role.SPARKY_ADMIN);
        userRepository.save(sparkyAdmin);

        authHeader = "Bearer " + jwtService.generateToken(sparkyAdmin);
    }

    @Test
    public void testGetAllCompanies() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/admin/companies").header("Authorization", authHeader))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2));
    }

    @Test
    public void testGetCompany() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/admin/companies/{id}", company1.getId()).header("Authorization",
                        authHeader))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.ruc").value(company1.getRuc()));

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/admin/companies/{id}", company2.getId()).header("Authorization",
                        authHeader))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.ruc").value(company2.getRuc()));

    }

}
