package org.sparky.sparkyai.admin.application;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import org.sparky.sparkyai.company.domain.Company;
import org.sparky.sparkyai.company.domain.CompanyService;
import org.sparky.sparkyai.company.dto.CompanyConsumptionDto;
import org.sparky.sparkyai.company.dto.CompanyResponseDto;
import org.sparky.sparkyai.company.dto.CreateCompanyWithAdminDto;
import org.sparky.sparkyai.company.dto.UpdateCompanyDto;

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
    public CompanyResponseDto createCompany(@Valid @RequestBody CreateCompanyWithAdminDto dto) {
        Company company = companyService.createCompanyWithAdmin(dto);
        return modelMapper.map(company, CompanyResponseDto.class);
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
    public CompanyResponseDto changeCompanyStatus(@PathVariable Long id, @RequestParam Boolean enable) {
        Company company = companyService.getCompanyById(id);
        Company updatedCompany = companyService.changeCompanyStatus(company, enable);
        return modelMapper.map(updatedCompany, CompanyResponseDto.class);
    }

    @GetMapping("/companies/{id}/consumption")
    public CompanyConsumptionDto getConsumptionReport(@PathVariable Long id) {
        Company company = companyService.getCompanyById(id);
        return companyService.getCompanyConsumption(company);
    }

}
