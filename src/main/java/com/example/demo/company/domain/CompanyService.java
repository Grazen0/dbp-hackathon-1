package com.example.demo.company.domain;

import com.example.demo.common.exception.ResourceNotFoundException;
import com.example.demo.company.dto.CreateCompanyDto;
import com.example.demo.company.dto.CreateCompanyWithAdminDto;
import com.example.demo.company.dto.UpdateCompanyDto;
import com.example.demo.company.infrastructure.CompanyRepository;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserService;

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
        User admin = userService.createUserWithoutSaving(dto.getMainAdmin(), company);

        company.setMainAdmin(admin);
        return companyRepository.save(company);
    }

    public Company updateCompany(Company company, UpdateCompanyDto updateDto) {
        company.setName(updateDto.getName());
        company.setRuc(updateDto.getRuc());

        if (company.getMainAdmin().getId() != updateDto.getMainAdminId()) {
            User newAdmin = userService.getUserById(updateDto.getMainAdminId());
            company.setMainAdmin(newAdmin);
        }

        return companyRepository.save(company);
    }

    public Company changeCompanyStatus(Company company) {
        company.setStatus(company.getStatus() == Status.ENABLED
                ? Status.DISABLED
                : Status.ENABLED);
        return companyRepository.save(company);
    }

}
