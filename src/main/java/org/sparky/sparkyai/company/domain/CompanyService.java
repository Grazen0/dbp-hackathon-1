package org.sparky.sparkyai.company.domain;

import org.sparky.sparkyai.common.exception.ResourceNotFoundException;
import org.sparky.sparkyai.company.dto.CreateCompanyDto;
import org.sparky.sparkyai.company.dto.CreateCompanyWithAdminDto;
import org.sparky.sparkyai.company.dto.UpdateCompanyDto;
import org.sparky.sparkyai.company.infrastructure.CompanyRepository;
import org.sparky.sparkyai.user.domain.User;
import org.sparky.sparkyai.user.domain.UserService;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    public Company getCompanyById(Long id) {
        return companyRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    public Company createCompanyWithAdmin(CreateCompanyWithAdminDto dto) {
        Company company = modelMapper.map(dto.getCompany(), Company.class);
        User admin = userService.createUser(dto.getAdmin());

        company.setAdmin(admin);
        return companyRepository.save(company);
    }

    public Company updateCompany(Company company, UpdateCompanyDto updateDto) {
        company.setName(updateDto.getName());
        company.setRuc(updateDto.getRuc());

        if (company.getAdmin().getId() != updateDto.getMainAdminId()) {
            User newAdmin = userService.getUserById(updateDto.getMainAdminId());
            company.setAdmin(newAdmin);
        }

        return companyRepository.save(company);
    }

    public Company changeCompanyStatus(Company company, Boolean enable) {
        company.setStatus(enable ? Status.ENABLED : Status.DISABLED);
        return companyRepository.save(company);
    }

}
