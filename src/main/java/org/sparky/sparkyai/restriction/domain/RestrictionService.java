package org.sparky.sparkyai.restriction.domain;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import org.sparky.sparkyai.common.exception.ResourceNotFoundException;
import org.sparky.sparkyai.company.domain.Company;
import org.sparky.sparkyai.restriction.dto.CreateRestrictionDto;
import org.sparky.sparkyai.restriction.dto.UpdateRestrictionDto;
import org.sparky.sparkyai.restriction.infrastructure.RestrictionRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
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

    public Restriction getRestrictionByIdAndCompanyId(Long id, Long companyId) {
        return restrictionRepository.findByIdAndCompanyId(id, companyId)
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
