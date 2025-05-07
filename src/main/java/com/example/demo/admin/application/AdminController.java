package com.example.demo.admin.application;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.demo.company.domain.Company;
import com.example.demo.company.domain.CompanyService;
import com.example.demo.company.dto.CompanyResponseDto;
import com.example.demo.company.dto.CreateCompanyWithAdminDto;
import com.example.demo.company.dto.UpdateCompanyDto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ROLE_SPARKY_ADMIN')")
@RequiredArgsConstructor
public class AdminController {

    private final CompanyService companyService;
    private final ModelMapper modelMapper;

    @GetMapping("/companies")
    public List<CompanyResponseDto> getAllCompanies() {
        List<Company> companies = companyService.getAllCompanies();
        return companies.stream()
                .map(company -> modelMapper.map(company, CompanyResponseDto.class))
                .toList();
    }

    @PostMapping("/companies")
    @ResponseStatus(HttpStatus.CREATED)
    public Company createCompany(@Valid @RequestBody CreateCompanyWithAdminDto dto) {
        return companyService.createCompanyWithAdmin(dto);
    }

    @GetMapping("/companies/{id}")
    public CompanyResponseDto getCompany(@PathVariable Long id) {
        Company company = companyService.getCompanyById(id);
        return modelMapper.map(company, CompanyResponseDto.class);
    }

    @PutMapping("/companies/{id}")
    public CompanyResponseDto updateCompany(@PathVariable Long id, @Valid @RequestBody UpdateCompanyDto companyDto) {
        Company company = companyService.getCompanyById(id);
        Company updatedCompany = companyService.updateCompany(company, companyDto);
        return modelMapper.map(updatedCompany, CompanyResponseDto.class);
    }

    @PatchMapping("/companies/{id}/status")
    public CompanyResponseDto changeCompanyStatus(@PathVariable Long id) {
        Company company = companyService.getCompanyById(id);
        Company updatedCompany = companyService.changeCompanyStatus(company);
        return modelMapper.map(updatedCompany, CompanyResponseDto.class);
    }

    @GetMapping("/companies/{id}/consumption")
    public void getConsumptionReport(@PathVariable Long id) {
        // TODO: martin o matias hola :D
    }

}
