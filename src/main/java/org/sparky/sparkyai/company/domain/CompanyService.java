package org.sparky.sparkyai.company.domain;

import org.sparky.sparkyai.common.exception.ResourceNotFoundException;
import org.sparky.sparkyai.company.dto.CompanyConsumptionDto;
import org.sparky.sparkyai.company.dto.CompanyResponseDto;
import org.sparky.sparkyai.company.dto.CreateCompanyDto;
import org.sparky.sparkyai.company.dto.CreateCompanyWithAdminDto;
import org.sparky.sparkyai.company.dto.UpdateCompanyDto;
import org.sparky.sparkyai.company.infrastructure.CompanyRepository;
import org.sparky.sparkyai.user.domain.Role;
import org.sparky.sparkyai.user.domain.User;
import org.sparky.sparkyai.user.domain.UserService;
import org.sparky.sparkyai.usercall.domain.UserCallService;
import org.sparky.sparkyai.usercall.dto.UserCallResponseDto;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final UserService userService;
    private final UserCallService userCallService;
    private final ModelMapper modelMapper;

    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    public Company getCompanyById(Long id) {
        return companyRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    public Company createCompanyWithAdmin(CreateCompanyWithAdminDto dto) {
        User admin = userService.createAdminUser(dto.getAdmin());
        Company company = modelMapper.map(dto.getCompany(), Company.class);
        company.setAdmin(admin);
        return companyRepository.save(company);
    }

    public Company updateCompany(Company company, UpdateCompanyDto updateDto) {
        company.setName(updateDto.getName());
        company.setRuc(updateDto.getRuc());
        return companyRepository.save(company);
    }

    public Company changeCompanyStatus(Company company, Boolean enable) {
        company.setStatus(enable ? Status.ENABLED : Status.DISABLED);
        return companyRepository.save(company);
    }

    public CompanyConsumptionDto getCompanyConsumption(Company company) {
        List<UserCallResponseDto> calls = userCallService.getCallsByCompanyId(company.getId())
                .stream()
                .map(call -> modelMapper.map(call, UserCallResponseDto.class))
                .toList();

        CompanyConsumptionDto report = new CompanyConsumptionDto();
        report.setCallHistory(calls);
        report.setTotalCalls(calls.size());
        report.setTotalConsumedTokens(calls.stream().map(call -> call.getConsumedTokens()).reduce(0, Integer::sum));
        return report;
    }

}
