package com.example.demo.restriction.domain;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.demo.common.exception.ResourceNotFoundException;
import com.example.demo.company.domain.Company;
import com.example.demo.restriction.dto.CreateRestrictionDto;
import com.example.demo.restriction.dto.UpdateRestrictionDto;
import com.example.demo.restriction.infrastructure.RestrictionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RestrictionService {

    private final RestrictionRepository restrictionRepository;
    private final ModelMapper modelMapper;

    public List<Restriction> getAllRestrictions() {
        return restrictionRepository.findAll();
    }

    public List<Restriction> getRestrictionsByCompanyId(Long companyId) {
        return restrictionRepository.findByCompanyId(companyId);
    }

    public Restriction getRestrictionById(Long id) {
        return restrictionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restriction not found"));
    }

    public Restriction createRestriction(CreateRestrictionDto restrictionDto, Company company) {
        Restriction restriction = modelMapper.map(restrictionDto, Restriction.class);
        restriction.setCompany(company);
        return restrictionRepository.save(restriction);
    }

    public Restriction updateRestriction(Restriction restriction, UpdateRestrictionDto restrictionDto) {
        // TODO: set fields
        return restrictionRepository.save(restriction);
    }

    public void deleteRestriction(Restriction restriction) {
        restrictionRepository.delete(restriction);
    }

}
